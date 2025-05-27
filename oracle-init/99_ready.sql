BEGIN
  -- Crea un archivo bandera cuando todo esté listo
  EXECUTE IMMEDIATE 'CREATE OR REPLACE DIRECTORY ORACLE_INIT_DIR AS ''/opt/oracle/oradata''';
  DECLARE
    fHandle UTL_FILE.FILE_TYPE;
  BEGIN
    fHandle := UTL_FILE.FOPEN('ORACLE_INIT_DIR', '.db_ready', 'W');
    UTL_FILE.PUT_LINE(fHandle, 'READY');
    UTL_FILE.FCLOSE(fHandle);
  END;
END;
/
