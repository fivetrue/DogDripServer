package com.fivetrue.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.Password;
import com.fivetrue.db.annotation.PrimaryKey;
import com.fivetrue.utils.TextUtils;

/**
 * 
 * @author kwonojin
 * 상속받은 Class는 반드시 Class 명을 Database Table 명과 동일하게 제작해야한다.
 * 또한 Class 멤버 변수는 Table column명과 동일하게 작성해야 정상적으로 동작한다.
 * @param <T>
 */ // 위에 주석은 왜 깨져서 보이는 거임? 응 저가 내가 급하게 학교에서 수정하느라 다박살남 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
public class DatabaseObject {
	
	private QueryBuilder builer = null;
	
	public void setObject(ResultSet rs){
		makeBuilder().setData(this, rs);
	}
	
	/**
	 * @return
	 */
	public QueryBuilder makeBuilder(){
		if(builer == null){
			builer = QueryBuilder.newInstance();
		}
		return builer; 
	}
	
	/**
	 * Insert Query를 생성하여 준다.
	 */
	public String insertQuery(){
		return makeBuilder().insertQuery(this);
	}
	
	/**
	 * Update Query를 생성하여 준다.
	 */
	public String updateQuery(){
		return makeBuilder().updateQuery(this);
	}
	
	/**
	 * Delete Query를 생성하여 준다.
	 */
	public String deleteQuery(){
		return makeBuilder().deleteQuery(this);
	}
	
	/**
	 * Select Query를 생성하여 준다.
	 */
//	public String selectQuery(String[] selections, String[] where){
	public String selectQuery(String[] selections, String where){
		return makeBuilder().selectQuery(getClass(), selections, where);
	}
	
	/**
	 * Mysql Query를 만들어주는 Builder Class
	 * @author Fivetrue
	 *
	 */
	public static class QueryBuilder{
		
		private ArrayList<DatabaseObject> relationDatas = null;
		private StringBuilder query = null;
		
		public static QueryBuilder newInstance(){
			return new QueryBuilder();
		}
		
		/**
		 * 현재 생성한 Database Object와 관련있는 data를 넣는다.
		 * Database에 Data insert 시 함께 추가된다.
		 * @param data
		 * @return
		 */
		public QueryBuilder addRelationData(DatabaseObject data){
			if(this.relationDatas == null){
				this.relationDatas = new ArrayList<DatabaseObject>();
			}
			this.relationDatas.add(data);
			return this;
		}
		
		/**
		 * Database에 Insert할 DatabaseObject를 넣는다.
		 * relationData 들과 함꼐 Insert 된다.
		 * @param data
		 * @return
		 */
		public String insertQuery(DatabaseObject data){
			query = makeInsertQuery(data);
			if(relationDatas != null){
				for(DatabaseObject d : relationDatas){
					query.append(makeInsertQuery(d).toString());	
				}
			}
			String q = query.toString();
			query = null;
			return q.trim();
		}
		
//		public String selectQuery(Class<? extends DatabaseObject> cls, String[] selections, String[] where){
		public String selectQuery(Class<? extends DatabaseObject> cls, String[] selections, String where){
			query = makeSelectQuery(cls, selections, where);
			String q = query.toString();
			query = null;
			return q;
		}
		
		public String updateQuery(DatabaseObject data){
			if(findPrimaryKey(data) == null){
				throw new IllegalArgumentException("databaseObject has to has primaryKey annotation");
			}
			query = makeUpdateQuery(data);
			String q = query.toString();
			query = null;
			return q;
			
		}
		
		public String deleteQuery(DatabaseObject data){
			if(findPrimaryKey(data) == null){
				throw new IllegalArgumentException("databaseObject has to has primaryKey annotation");
			}
			query = makeDeleteQuery(data);
			String q = query.toString();
			query = null;
			return q;
		}
		
		private StringBuilder makeSelectQuery(Class<? extends DatabaseObject> cls, String[] selections, String where){
			StringBuilder sb = new StringBuilder();
			if(cls != null){
				sb.append("select ");
				if(selections != null){
					for(int i = 0 ; i < selections.length ; i++){
						String s = selections[i];
						sb.append(s);
						if(i < selections.length - 1){
							sb.append(",");
						}
					}
				}else{
					sb.append("* ");
				}
				
				ArrayList<Field> fs = findForeignFields(cls);
				if(fs != null && fs.size() > 0){
					sb.append("from " + cls.getSimpleName().toLowerCase());
					sb.append(makeInnerJoinQuery(cls));
				}else{
					sb.append("from " + cls.getSimpleName().toLowerCase());
				}
				
				if(!TextUtils.isEmpty(where)){
					sb.append(" where ").append(where);
					
//					for(int i = 0 ; i < where.length ; i++){
//						sb.append(where[i]);
//						if(i < where.length - 1){
//							sb.append(" or ");
//						}
//					}
				}
				
				//TODO : Group by
			}
			return sb;
		}
		
		private String makeInnerJoinQuery(Class<? extends DatabaseObject> data){
			String q = null;
			if(data != null){
				ArrayList<Field> fields = findForeignFields(data);
				if(fields != null && fields.size() > 0){
					StringBuilder sb = new StringBuilder();
					for(Field f : fields){
						ForeignKey key = f.getAnnotation(ForeignKey.class);
						if(key != null){
							Class foreignClass = key.value();
							ArrayList<Field> fPrimary = findPrimaryKey(foreignClass);
							if(fPrimary != null && fPrimary.size() > 0){
								sb.append(" inner join " + foreignClass.getSimpleName().toLowerCase() + " ")
								.append("on " + data.getSimpleName().toLowerCase() + "." + f.getName() + "=" + foreignClass.getSimpleName().toLowerCase() + "." + fPrimary.get(0).getName());
								
							}
						}
						q = sb.toString();
					}
				}
//				fields = findMemberField(data);
//				if(fields != null && fields.size() > 0){
//					for(Field f : fields){
//						Class<? extends DatabaseObject> jCls = (Class)f.getType();
//						String subQ = makeInnerJoinQuery(jCls);
//						if(subQ != null){
//							q += makeInnerJoinQuery(jCls);
//						}
//					}
//				}
			}
			return q;
		}
		
		private void setData(DatabaseObject data, ResultSet rs){
			if(data != null && rs != null){
				Field[] fields = data.getClass().getDeclaredFields();
				if(fields != null){
					for(int i = 0 ; i < fields.length ; i++){
						Field f = fields[i];
						f.setAccessible(true);
						String k = f.getName();
						Object v = null;
						if(checkMemberVarable(f)){
							try {
								v = f.getType().newInstance();
								if(v instanceof DatabaseObject){
									((DatabaseObject) v).setObject(rs);
								}
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							try {
								v = rs.getObject(k);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						try {
							f.set(data, v);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		}
		
		private ArrayList<Field> findPrimaryKey(Class<? extends DatabaseObject> data){
			ArrayList<Field> f = new ArrayList<Field>();
			if(data != null){
				for(Field f1 : data.getDeclaredFields()){
					f1.setAccessible(true);
					for(Annotation a : f1.getAnnotations()){
						if(a instanceof PrimaryKey){
							f.add(f1);
						}
					}
				}
			}
			return f;
		}
		
		private ArrayList<Field> findPrimaryKey(DatabaseObject data){
			return findPrimaryKey(data.getClass());
		}
		
		
		private ArrayList<Field> findForeignFields(Class<?> data){
			ArrayList<Field> fs = new ArrayList<Field>();
			if(data != null){
				Field[] fields = data.getDeclaredFields();
				if(fields != null){
					for(int i = 0 ; i < fields.length ; i ++){
						Field f = fields[i];
						f.setAccessible(true);
						for(Annotation a : f.getAnnotations()){
							if(a instanceof ForeignKey){
								fs.add(f); 
							}
						}
					}
				}
			}
			return fs;
		}
		
		private ArrayList<Field> findMemberField(Class<? extends DatabaseObject> data){
			ArrayList<Field> fs = new ArrayList<Field>();
			if(data != null){
				Field[] fields = data.getClass().getDeclaredFields();
				if(fields != null){
					for(int i = 0 ; i < fields.length ; i ++){
						Field f = fields[i];
						f.setAccessible(true);
						for(Annotation a : f.getAnnotations()){
							if(a instanceof MemberVariable){
								fs.add(f);  
							}
						}
					}
				}
			}
			return fs;
		}
		
		private StringBuilder makeInsertQuery(DatabaseObject data){
			StringBuilder sb = new StringBuilder();
			if(data != null){
				Field[] fields = data.getClass().getDeclaredFields();
				String keys = "";
				String values = "";
				if(fields != null){
//					sb.append("insert into ").append(data.getClass().getSimpleName().toLowerCase()).append(" values(");
					sb.append("insert into ").append(data.getClass().getSimpleName().toLowerCase());
					for(int i = 0 ; i < fields.length ; i++){
						Field f = fields[i];
						if(checkMemberVarable(f)){
							continue;
						}
						f.setAccessible(true);
						
						boolean isAutoIncrement = false;
						boolean isPassword = false;
						
						for(Annotation a : f.getAnnotations()){
							isAutoIncrement = (a instanceof AutoIncrement);
						}
						
						for(Annotation a : f.getAnnotations()){
							isPassword = (a instanceof Password);
						}
						
						if(isAutoIncrement){
							continue;
						}
						
						String key = f.getName();
						Object value = getValue(data, f);
						Type type = f.getType();

						if(key != null && type != null){
							keys += key ;
							
							if(type.toString().contains("String")){
								if(isPassword){
									value = "password('" + value + "')";
								}else{
									value = "'" + value + "'";
								}
								values += value;
							}else{
								if(isPassword){
									value = "password(" + value + ")";
								}
								values += value == null ? 0 : value;
							}

							if(i < fields.length -1){
								keys += ",";
								values += ",";
//								sb.append(",");
							}
						}
					}
				}
				sb.append(" (").append(keys);
				if(sb.charAt(sb.length() - 1) == ','){
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append(") values (").append(values);
				if(sb.charAt(sb.length() - 1) == ','){
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append(");");
			}
			return sb;
		}
		
		private StringBuilder makeUpdateQuery(DatabaseObject data){
			return makeUpdateQuery(data, null);
		}
		
		private StringBuilder makeUpdateQuery(DatabaseObject data, String where){
			StringBuilder sb = new StringBuilder();
			if(data != null){
				Field[] fields = data.getClass().getDeclaredFields();
				if(fields != null){
					sb.append("update ").append(data.getClass().getSimpleName().toLowerCase()).append(" set ");
					for(int i = 0 ; i < fields.length ; i++){
						Field f = fields[i];
						f.setAccessible(true);
						
						if(checkMemberVarable(f)){
							if(i == fields.length -1){
								sb.replace(sb.length() - 1, sb.length(), "");
							}
							continue;
						}else{
							boolean isAutoIncrement = false;
							boolean isPrimaryKey = false;
							
							for(Annotation a : f.getAnnotations()){
								isAutoIncrement = (a instanceof AutoIncrement);
								isPrimaryKey = (a instanceof PrimaryKey);
							}
							
							if(isAutoIncrement || isPrimaryKey){
								continue;
							}
							
							String key = f.getName();
							Object value = getValue(data, f);
							Type type = f.getType();

							if(key != null && type != null){
								sb.append(key).append("=");
								if(isString(f)){
									sb.append("'").append(value).append("'");
								}else{
									sb.append(value == null ? 0 : value);
								}

								if(i < fields.length -1){
									sb.append(",");
								}
							}
						}
					}
					
					if(!TextUtils.isEmpty(where)){
						sb.append(" where " + where);
					}else{
						ArrayList<Field> pks = findPrimaryKey(data);
						if(pks != null && pks.size() > 0){
							where = "";
							for(int i = 0 ; i < pks.size() ; i++){
								Field f = pks.get(i);
								f.setAccessible(true);
								String key = f.getName();
								Object value = getValue(data, f);
								Type type = f.getType();
								if(key != null && type != null){
									where += key + "=";
									if(isString(f)){
										where += "'" + value + "'";
									}else{
										where += value == null ? 0 : value;
									}

									if(i < pks.size() - 1){
										where += " and ";
									}
								}

							}
							if(!TextUtils.isEmpty(where)){
								sb.append(" where ").append(where);
							}
						}
					}
					sb.append(";");
					
//					Field primaryField = findPrimaryKey(data);
//					Object v = getValue(data, primaryField);
//					if(primaryField != null && v != null){
//						sb.append(" where " + primaryField.getName() + "=");
//						if(isString(primaryField)){
//							sb.append("'").append(v).append("'");
//						}else{
//							sb.append(v);
//						}
//					}
//					sb.append(";");
					
//					for(Field f : fields){
//						if(checkMemberVarable(f)){
//								DatabaseObject<?> obj = (DatabaseObject<?>) getValue(data, f);
//								sb.append(makeUpdateQuery(obj));
//						}
//					}
				}
			}
			return sb;
		}
		
		private StringBuilder makeDeleteQuery(DatabaseObject data){
			StringBuilder sb = new StringBuilder();
			if(data != null){
				Field[] fields = data.getClass().getDeclaredFields();
				if(fields != null){
					sb.append("delete from ").append(data.getClass().getSimpleName().toLowerCase()).append(" ");
					ArrayList<Field> primaryKey = findPrimaryKey(data);
					if(primaryKey != null && primaryKey.size() > 0){
						sb.append("where ");
						for(int i = 0 ; i < primaryKey.size() ; i ++){
							Field pk = primaryKey.get(0);
							String key = pk.getName();
							Object value = getValue(data, pk);
							sb.append(key + "=");
							if(isString(pk)){
								sb.append("'").append(value).append("'");
							}else{
								sb.append(value);
							}
							
							if(i < primaryKey.size() - 1){
								sb.append(" and ");
							}
						}
						sb.append(";");
					}
				}
			}
			return sb;
		}
		
		private boolean isString(Field f){
			boolean b = false;
			if(f != null){
				b = f.getType().toString().contains("String");
			}
			return b;
		}
		
		private Object getValue(Object data, Field f){
			Object v = null;
			try {
				v = f.get(data);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;
		}
		
		private boolean checkMemberVarable(Field f){
			boolean b = false;
			f.setAccessible(true);
			return f != null && f.getAnnotation(MemberVariable.class) != null;
		}
	}
}
