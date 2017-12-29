package com.codigosandroid.gensyspdv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.codigosandroid.utils.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Tiago on 22/12/2017.
 */

public class BaseDAO {

    private static final String TAG = BaseDAO.class.getSimpleName();
    // Database
    private static final String DB_NAME = "genius_pdv.db";
    // Tabelas
    public static final String TABLE_CFBLOB = "CFBLOB";
    public static final String TABLE_CLIENTE = "CLIENTE";
    public static final String TABLE_EMPRESA = "EMPRESA";
    public static final String TABLE_ESTOQUE = "ESTOQUE";
    public static final String TABLE_ESTOQUE_PRECO = "ESTOQUE_PRECO";
    public static final String TABLE_FORMA_PAGAMENTO = "FORMA_PAGAMENTO";
    public static final String TABLE_PRECO_HORA = "PRECO_HORA";
    public static final String TABLE_PROMOCOES = "PROMOCOES";
    public static final String TABLE_PYDETALHE = "PYDETALHE";
    public static final String TABLE_PYRECPAG = "PYRECPAG";
    public static final String TABLE_PYVENDA = "PYVENDA";
    public static final String TABLE_TIPO_PAGAMENTO = "TIPO_PAGAMENTO";
    public static final String TABLE_TIPO_USUARIO = "TIPO_USUARIO";
    public static final String TABLE_USUARIO = "USUARIO";
    // Versão do db
    private static final int DB_VERSION = 1;
    // Scripts de criação
    private static final String[] CREATE_SCRIPT = new String[] {
            "PRAGMA foreing_keys = ON",

            "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_USUARIO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRICAO TEXT UNIQUE);",

            "CREATE TABLE IF NOT EXISTS " + TABLE_USUARIO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_TIPO_USUARIO INTEGER, "
                    + "NOME TEXT, "
                    + "APELIDO TEXT, "
                    + "SENHA TEXT, "
                    + "EMAIL TEXT, "
                    + "TIPO TEXT, "
                    + "ID_VENDEDOR INTEGER, "
                    + "FOREIGN KEY (ID_TIPO_USUARIO) REFERENCES TIPO_USUARIO (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTE + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOME TEXT, "
                    + "FANTASIA TEXT, "
                    + "CPF TEXT, "
                    + "CGC TEXT, "
                    + "PRECO TEXT, "
                    + "ID_CLIENTE INTEGER, "
                    + "TABELA_PRECO INTEGER);",

            "CREATE TABLE IF NOT EXISTS " + TABLE_EMPRESA + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRICAO TEXT, "
                    + "NFCE_TOKEN TEXT, "
                    + "NFCE_CSC TEXT, "
                    + "ID_EMPRESA INTEGER);",

            "CREATE TABLE IF NOT EXISTS " + TABLE_ESTOQUE + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "RECNO INTEGER, "
                    + "ID_EMPRESA INTEGER, "
                    + "CODIGO TEXT, "
                    + "CODIGO_FORNECEDOR TEXT, "
                    + "EMPRESA TEXT, "
                    + "UNIDADE TEXT, "
                    + "DESCRICAO TEXT, "
                    + "SALDO REAL, "
                    + "RESERVADO REAL, "
                    + "PRECO_CUSTO REAL, "
                    + "PRECO_VENDA REAL, "
                    + "MINIMO REAL, "
                    + "PRECO_COMPRA REAL, "
                    + "COBRAR_ICMS TEXT, "
                    + "TRIBUTARIA TEXT, "
                    + "DETALHADA TEXT, "
                    + "ATUALIZADO TEXT, "
                    + "SUBGRUPO TEXT, "
                    + "GRUPO TEXT, "
                    + "LOCALIZACAO TEXT, "
                    + "COMISSAO_PRODUTO REAL, "
                    + "OBSERVACAO TEXT, "
                    + "ESTOQUE_EXTRA_1 TEXT, "
                    + "PRECO_PROMOCAO REAL, "
                    + "PRECO_PROMOCAO_REVENDA REAL, "
                    + "PRECO_REVENDA REAL, "
                    + "PESAVEL TEXT, "
                    + "FORNECEDOR TEXT, "
                    + "COBRAR_ICMS_SUBSTITUICAO TEXT, "
                    + "EMBALAGEM TEXT, "
                    + "INDIC_ARREDONDAMENTO TEXT, "
                    + "INDIC_PRODUCAO TEXT, "
                    + "PROMOCAO_INICIO TEXT, "
                    + "PROMOCAO_FIM TEXT, "
                    + "PRECO_MINIMO REAL, "
                    + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_ESTOQUE_PRECO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TABELA_CODIGO INTEGER, "
                    + "ID_EMPRESA INTEGER, "
                    + "CODIGO TEXT, "
                    + "DESCRICAO TEXT, "
                    + "PRECO REAL, "
                    + "EMPRESA TEXT, "
                    + "MENOR_PRECO REAL, "
                    + "AJUSTE_PERCENTUAL REAL, "
                    + "MARGEM_LUCRO REAL, "
                    + "ATUALIZADO TEXT, "
                    + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_PYVENDA + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "IDENTIFICADOR TEXT, "
                    + "TIPO TEXT, "
                    + "DATA_EMISSAO TEXT, "
                    + "ID_CLIENTE INTEGER, "
                    + "ID_USUARIO INTEGER, "
                    + "ID_OPERADOR INTEGER, "
                    + "ID_EMPRESA INTEGER, "
                    + "NUMERO_SERVIDOR TEXT, "
                    + "TOTAL REAL, "
                    + "CAPTURA TEXT, "
                    + "OBSERVACAO TEXT, "
                    + "STATUS INTEGER, "
                    + "NOTA_FISCAL TEXT, "
                    + "FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE (_ID), "
                    + "FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO (_ID), "
                    + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_PYDETALHE + " (_ID INTEGER PRIMARY KEY NOT NULL, "
                    + "ID_PYVENDA INTEGER, "
                    + "ID_ESTOQUE INTEGER, "
                    + "QTDE REAL, "
                    + "VLDESCONTO REAL, "
                    + "DESCONTO REAL, "
                    + "VALOR REAL, "
                    + "TOTAL REAL, "
                    + "FOREIGN KEY (ID_ESTOQUE) REFERENCES ESTOQUE (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_PAGAMENTO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRICAO TEXT, "
                    + "TIPO_PAGAMENTO TEXT, "
                    + "ID_FORMAPAG INTEGER);",

            "CREATE TABLE IF NOT EXISTS " + TABLE_FORMA_PAGAMENTO + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_TIPO_PAGAMENTO INTEGER,  "
                    + "VALOR REAL, "
                    + "PARCELA INTEGER, "
                    + "NUM_AUTORIZACAO TEXT, "
                    + "FOREIGN KEY (ID_TIPO_PAGAMENTO) REFERENCES TIPO_PAGAMENTO (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_PYRECPAG + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_PYVENDA INTEGER, "
                    + "ID_TIPO_PAGAMENTO INTEGER, "
                    + "VALOR REAL, "
                    + "FOREIGN KEY (ID_PYVENDA) REFERENCES PYVENDA (_ID), "
                    + "FOREIGN KEY (ID_TIPO_PAGAMENTO) REFERENCES TIPO_PAGAMENTO (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_PRECO_HORA + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_ESTOQUE INTEGER, "
                    + "DIA_SEMANA TEXT, "
                    + "HORA_INICIO TEXT, "
                    + "HORA_FIM TEXT, "
                    + "VALOR REAL, "
                    + "FOREIGN KEY (ID_ESTOQUE) REFERENCES ESTOQUE (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_PROMOCOES + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_EMPRESA INTEGER, "
                    + "ID_ESTOQUE INTEGER,  "
                    + "DATA_INICIO TEXT, "
                    + "DATA_FIM TEXT, "
                    + "NOME_PROMOCAO TEXT, "
                    + "OPERADOR TEXT, "
                    + "DATA_CRIACAO TEXT, "
                    + "OPERADOR_ALTERACAO TEXT, "
                    + "DATA_ALTERACAO TEXT, "
                    + "VALOR REAL, "
                    + "HORA_INICIO TEXT, "
                    + "HORA_FIM TEXT, "
                    + "FOREIGN KEY (ID_EMPRESA) REFERENCES EMPRESA (_ID), "
                    + "FOREIGN KEY (ID_ESTOQUE) REFERENCES ESTOQUE (_ID));",

            "CREATE TABLE IF NOT EXISTS " + TABLE_CFBLOB + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ID_CFBLOB INTEGER, "
                    + "CONFIGURACAO TEXT, "
                    + "PRINCIPAL TEXT, "
                    + "SECUNDARIO TEXT, "
                    + "TERCIARIO TEXT, "
                    + "QUATERNARIO TEXTO, "
                    + "MOBILE TEXT, "
                    + "NOME TEXT, "
                    + "STATUS TEXT, "
                    + "TESTADO TEXT, "
                    + "DATA TEXT);"
    };
    // Instancia DBHelper
    private DBHelper dbHelper;
    // Instancia SQLite
    protected SQLiteDatabase db;
    // Context
    protected Context context;
    // Contador
    private static int contador;

    public BaseDAO(Context context) {
        this.context = context;
    }

    protected synchronized void open() {
        if (db == null || (db != null && !db.isOpen())) {
            dbHelper = new DBHelper(context, DB_NAME, CREATE_SCRIPT, DB_VERSION);
            db = dbHelper.getWritableDatabase();
            numeroConexoes(+1);
        }
    }

    protected synchronized void close() {
        if (db != null && db.isOpen() && (numeroConexoes(0) == 1)) {
            dbHelper.close();
            db.close();
            numeroConexoes(-1);
        }
    }

    private synchronized static int numeroConexoes(int i) {
        contador = contador + i;
        return contador;
    }

    public static void exportDB(Context context) {

        try {

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//com.codigosandroid.gensyspdv//databases//" + DB_NAME;
                String backupDBPath = "/Download/" + DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                LogUtil.debug(TAG, currentDB.getAbsolutePath());
                LogUtil.debug(TAG, backupDB.getAbsolutePath());

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                FileInputStream fis = new FileInputStream(backupDB);
                FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/backup.zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                zipOut.putNextEntry(new ZipEntry(DB_NAME));

                int content;

                while ((content = fis.read()) != -1) {

                    zipOut.write(content);

                }

                zipOut.closeEntry();
                zipOut.close();

                Toast.makeText(context, "Backup Successfull!", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {

            LogUtil.error(TAG, e.getMessage(), e);
            Toast.makeText(context, "Backup Failed!", Toast.LENGTH_SHORT).show();

        }

    }

}
