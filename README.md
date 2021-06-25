# SimpleSqlite 

Access Android's Sqlite Database in an easy way, avoid boilerplate code and make your project look cleaner.

<b>How to use:</b>

    MyDataClass data = SimpleSqlite.get(
               Database.getInstance().getReadableDatabase(),
               "SELECT * FROM <TABLE_NAME> WHERE...",
               MyDataClass.class);
               
    //Get Results As a List           
    ArrayList<MyDataClass> dataList = SimpleSqlite.getList(
               Database.getInstance().getReadableDatabase(),
               "SELECT * FROM <TABLE_NAME> WHERE...",
               MyDataClass.class);



You will need:
 
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    
    
 
