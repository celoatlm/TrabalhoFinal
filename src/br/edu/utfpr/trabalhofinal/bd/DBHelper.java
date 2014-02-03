package br.edu.utfpr.trabalhofinal.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe auxiliar para gerenciar a cria��o de banco de dados e gerenciamento de vers�o.
 * Esta subclasse de SQLiteOpenHelper subscreve os m�todos onCreate(SQLiteDatabase), 
 * onUpgrade(SQLiteDatabase, int, int) e, opcionalmente, onOpen(SQLiteDatabase).
 * Esta classe se encarrega de abrir o banco de dados se ele existir, caso contr�rio ela
 * cria o mesmo, al�m de atualiz�-lo, quando necess�rio.
 * 
 * Nota: esta classe assume monotonicamente crescente n�meros de vers�o para upgrades.
 * @author Ricardo Sobjak
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	/**
	 * Indica o nome do banco de dados
	 */
	private static final String DATABASE_NAME = "appOportunidade.db";
	
	/**
	 * Indica a vers�o do banco de dados
	 */
	private static final int DATABASE_VERSION = 1;
	
	
	/**
	 * Strings que determinam o nome de cada uma das tabelas;
	 */
	public static final String TABLE_CURSO = "tb_curso";
	public static final String TABLE_CATEGORIA = "tb_categoria";
	public static final String TABLE_OPORTUNIDADE = "tb_oportunidade";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
	
	
	/**
	 * SQL para cria��o da tabela de Curso
	 */
	private static final String SQL_TABLE_EVENTO_CURSO = "CREATE TABLE " + TABLE_CURSO
			+ " ( id INTEGER PRIMARY KEY , descricao TEXT NOT NULL);";

	/**
	 * SQL para cria��o da tabela de Categorias
	 */
	private static final String SQL_TABLE_CATEGORIA_CREATE = "CREATE TABLE " + TABLE_CATEGORIA
			+ " ( id INTEGER PRIMARY KEY , descricao TEXT NOT NULL);";
	/**
	 * SQL para cria��o da tabela de Oportunidades
	 */
	private static final String SQL_TABLE_OPORTUNIDADE_CREATE = "CREATE TABLE " + TABLE_OPORTUNIDADE
			+ "( id INTEGER PRIMARY KEY , "
			+ " descricao TEXT NOT NULL,"
			+ " categoriaId INTEGER NOT NULL, "
			+ " cursoId INTEGER NOT NULL, "
			+ " FOREIGN KEY(categoriaId) REFERENCES " + TABLE_CATEGORIA + " (id) "
			+ " FOREIGN KEY(cursoId) REFERENCES " + TABLE_CURSO + " (id) );";
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	/**
	 * Este m�todo � executado na fase de cria��o do Bando de Dados.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_TABLE_EVENTO_CURSO);
		db.execSQL(SQL_TABLE_CATEGORIA_CREATE);
		db.execSQL(SQL_TABLE_OPORTUNIDADE_CREATE);
		
		db.execSQL("INSERT into " + TABLE_CATEGORIA + "(descricao) values ('Estagio');");
		db.execSQL("INSERT into " + TABLE_CATEGORIA + "(descricao) values ('Emprego');");
		db.execSQL("INSERT into " + TABLE_CATEGORIA + "(descricao) values ('Outros');");
		
		db.execSQL("INSERT into " + TABLE_CURSO + "(descricao) values ('Sistemas');");
		db.execSQL("INSERT into " + TABLE_CURSO + "(descricao) values ('Alimentos');");
		db.execSQL("INSERT into " + TABLE_CURSO + "(descricao) values ('Servidor');");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE + TABLE_OPORTUNIDADE);
		db.execSQL(DROP_TABLE + TABLE_CATEGORIA);
		db.execSQL(DROP_TABLE + TABLE_CURSO);
		
		onCreate(db);
	}
}
