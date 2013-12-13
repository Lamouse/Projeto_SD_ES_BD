/*select addPendingTransaction(1,1,100,0.01,0.02) from dual;*/

CREATE OR REPLACE FUNCTION addPendingTransaction(IDUser in Number, IDIdea in Number, nrShare in Number, newPrice in Real, maxPrice in Real)
	RETURN NUMBER IS
	PRAGMA AUTONOMOUS_TRANSACTION;
	l_var NUMBER;
BEGIN
	l_var:=GETIDEAVALUE(IDIdea);
	IF l_var > 0 THEN
		MERGE INTO PENDING_TRANSACTION p
		USING (SELECT IDUser "IDUSER", IDIdea "IDIDEIA", nrShare "NRSHARE", newPrice "NEWPRICE", maxPrice "MAXPRICE" FROM dual) d
		ON (p.IDUSER = d.IDUSER and p.IDIDEIA = d.IDIDEIA)
		WHEN MATCHED THEN
			UPDATE SET p.NEWPRICE = d.newPrice,
				p.MAXPRICE = d.maxPrice,
				p.NR_SHARE = d.nrShare
		WHEN NOT MATCHED THEN
			INSERT VALUES(d.IDUSER, d.IDIDEIA, d.newPrice, d.maxPrice, d.nrShare) WHERE (d.nrShare != 0);
		COMMIT;
		RETURN 1;
	ELSE
		RETURN 0;
	END IF;
EXCEPTION
	WHEN OTHERS THEN
		ROLLBACK;
		RETURN 0;
END;

/* ------------------------------------------------------------------------------------------------------------------------------ */

/*select GETIDSHARE(1,1) from dual;*/

CREATE OR REPLACE FUNCTION getIDShare(IDU in Number, IDI in Number)
	RETURN NUMBER IS
	l_var NUMBER;
BEGIN
	SELECT IDUSER_SHARE INTO l_var FROM USER_SHARE WHERE IDIDEIA = IDI AND IDUSER = IDU;
	RETURN l_var;
EXCEPTION
	WHEN OTHERS THEN
		RETURN 0;
END;

/* ------------------------------------------------------------------------------------------------------------------------------ */

/*EXEC BUYSHARES(1,2,1,100,0.02,'data');*/

CREATE OR REPLACE PROCEDURE BUYSHARES(idVende in Number, idCompra in Number, ideia in Number, nrShare in Number, newPrice in Real, data in Varchar2, valor out Real)
IS
	share0 number :=0;
	share1 number :=0;
	dinh real :=0;
BEGIN
	valor := 0;
	share0 := GETIDSHARE(idVende,ideia);
	IF share0 > 0 THEN
		SELECT PRICE INTO dinh FROM USER_SHARE WHERE IDUSER_SHARE = share0; 
		valor := dinh*nrShare;
		share1 := GETIDSHARE(idCompra,ideia);
		IF share1 = 0 THEN
			INSERT INTO USER_SHARE VALUES(CUSER_SHARE.NEXTVAL,ideia,idCompra,newPrice,nrShare);
		ELSE
			UPDATE USER_SHARE SET NR_SHARE = NR_SHARE + nrShare WHERE IDUSER_SHARE = share1;
		END IF;
		UPDATE USER_SHARE SET NR_SHARE = NR_SHARE - nrShare WHERE IDUSER_SHARE = share0;
		UPDATE UTILIZADOR SET DINHEIRO = DINHEIRO - valor WHERE IDUSER = idCompra;
		UPDATE UTILIZADOR SET DINHEIRO = DINHEIRO + valor WHERE IDUSER = idVende;
		UPDATE IDEIA SET IS_FIRST = 0, VALOR_MERCADO = dinh WHERE IDIDEIA = ideia;
		INSERT INTO TRANSACCOES VALUES (idVende, 'Utilizador vendeu '||nrShare||' shares da ideia '||ideia||' por '||valor||' DEIcoins ao utilizador '||idCompra, data);
		INSERT INTO TRANSACCOES VALUES (idCompra, 'Utilizador comprou  '||nrShare||' shares da ideia '||ideia||' por '||valor||' DEIcoins ao utilizador '||idVende, data);
		COMMIT;
	END IF;
EXCEPTION
	WHEN OTHERS THEN
		ROLLBACK;
END;

/* ------------------------------------------------------------------------------------------------------------------------------ */

/*select GETIDEAVALUE(1) from dual;*/

CREATE OR REPLACE FUNCTION GETIDEAVALUE(IDI in Number)
	RETURN NUMBER IS
	l_var NUMBER;
BEGIN
	SELECT VALOR_MERCADO INTO l_var FROM IDEIA WHERE IDIDEIA = IDI;
	RETURN l_var;
EXCEPTION
	WHEN OTHERS THEN
		RETURN 0;
END;

/* ------------------------------------------------------------------------------------------------------------------------------ */

CREATE OR REPLACE PROCEDURE CHANGE_SHARE_PRICE 
	(PESSOAID IN UTILIZADOR.IDUSER%TYPE, 
	IDEIAID IN IDEIA.IDIDEIA%TYPE, 
	NEWPRICE IN PENDING_TRANSACTION.NEWPRICE%TYPE)

	IS
	R_CENAS IDEIA.IS_FIRST%TYPE;
BEGIN
	IF NEWPRICE > 0 THEN
		SELECT IS_FIRST INTO R_CENAS FROM IDEIA WHERE IDIDEIA = IDEIAID;
		IF R_CENAS = 1 THEN
			UPDATE USER_SHARE SET PRICE = NEWPRICE WHERE IDUSER =  PESSOAID  AND IDIDEIA =  IDEIAID;
     		COMMIT;
  		END IF;
	END IF;
EXCEPTION
	WHEN OTHERS THEN
		ROLLBACK;
END;

/* ------------------------------------------------------------------------------------------------------------------------------ */

CREATE OR REPLACE PROCEDURE CREATE_NEW_IDEIA 
	(IDEIAID IN IDEIA.IDIDEIA%TYPE, IDEIATEXT IN IDEIA.MENSAGEM%TYPE, 
	STARTSHARES IN IDEIA.TOTAL_SHARE%TYPE, VALUE IN IDEIA.INIT_VALUATION%TYPE, 
	PERSON IN UTILIZADOR.IDUSER%TYPE, AUX_STR IN TRANSACCOES.DATA%TYPE, 
	NEWNAME IN IDEIA.ANEXO%TYPE,
	ATTRIBUTEID IN USER_SHARE.IDUSER_SHARE%TYPE) 
	IS
BEGIN
	INSERT INTO IDEIA VALUES(IDEIAID, IDEIATEXT, STARTSHARES, VALUE, 1, 0, VALUE, PERSON, AUX_STR, NEWNAME);
	INSERT INTO USER_SHARE VALUES(ATTRIBUTEID, IDEIAID, PERSON, VALUE, STARTSHARES);
	COMMIT;
EXCEPTION
	WHEN OTHERS THEN
		ROLLBACK;
END;
