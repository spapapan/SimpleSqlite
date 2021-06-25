# SimpleSqlite 

Access Android's Sqlite Database in an easy generic way, avoid boilerplate code and make your project look cleaner.

<h2>How to use:</h2>

    MyDataClass data = SimpleSqlite.get(
               Database.getInstance().getReadableDatabase(),
               "SELECT * FROM <TABLE_NAME> WHERE...",
               MyDataClass.class);
               
    //Get Results As a List           
    ArrayList<MyDataClass> dataList = SimpleSqlite.getList(
               Database.getInstance().getReadableDatabase(),
               "SELECT * FROM <TABLE_NAME> WHERE...",
               MyDataClass.class);

<h2>Be careful!</h2>

The field names of your custom class "MyDataClass" must be the same as your database table's names. If there are any inconsistencies a message will appear in the console explaining what went wrong.

<h2>You will need:</h2>
 
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    
    
 
