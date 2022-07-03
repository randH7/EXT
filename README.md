# EXT

Expiry Date Tracker Android 🍞🍎🥛<br/>

![main](https://user-images.githubusercontent.com/107724456/177052058-a51d5c10-12e6-4094-906b-0023b3de2fe8.png)


## About

The purpose of this project is to reduce food waste by using a application 
(Expiry date tracker) that displays a list of foodstuffs you own together with their expiry dates.<br/>


## Timeline

14 Mar 2022 – 27 Mar 2022<br/>


## How to use it ?


### • Registering a Product Information

To save your food information, you can write name and quantity of the food.
After that, set the expiration date.<br/>

![Registering a product information](https://user-images.githubusercontent.com/107724456/177052119-7565d626-648a-4dc5-a471-c45c9ff191a2.png)


### • Registered Product List

As below image, you can see food name, quantity and expiration date.<br/>

![Registered product list](https://user-images.githubusercontent.com/107724456/177052134-cedbe2f4-05ce-4879-a03c-2cf381eb7fd0.png)


### • Update and Delete

In Edit Food page, edit information and click "Update" button. Also, you can delet it by click "Delete" button.<br/>

![Update and Delete](https://user-images.githubusercontent.com/107724456/177052165-0540db28-35e7-4814-a911-e3359b862056.png)


### • Notification for expiry

You can select the day before expiry foods, It will show red expiration date in List Food page.<br/>

![Notification for expiry](https://user-images.githubusercontent.com/107724456/177052160-71c22947-91b8-489d-8e4a-2dbc4bae4bec.png)


## About Design

### • Logo

I design this logo by canva.<br/>

<img width="250" alt="LOGO" src="https://user-images.githubusercontent.com/107724456/177057921-f3167c51-2e28-489f-a03a-2ebaed87abde.png">


### • Main Color

I decided to use red and green, the red color naturally gives users pause due to its connotations. Green, on the other hand, symbolizes growth, safety, a success state and generally a positive outcome..<br/>
Then I used this site to find color pattern: https://pigment.shapefactory.co/<br/>

<img width="300" alt="Colors" src="https://user-images.githubusercontent.com/107724456/177058381-9b2c5991-8030-452a-8ff3-c92bfb8f8d3d.png">


## Development

### • Architecture

MVC is used in this app,
And this is my directory.<br/>

<img align="left" width="200" alt="directory" src="https://user-images.githubusercontent.com/107724456/177056913-03d59d82-4ffc-49d4-bccc-eed63140a4a1.png">
<img width="240" alt="directory 2" src="https://user-images.githubusercontent.com/107724456/177057189-8aa09dfe-b7eb-4fa7-95dd-b2ea09c8eeb6.png">
<br>

### • Class diagram

<img width="800" alt="Class diagram" src="https://user-images.githubusercontent.com/107724456/177056919-ee7a99eb-ae90-43df-a494-cc4977556a3d.png">

<br>

### • Database

This is ER diagram of the project.<br/>

<img width="500" alt="ER diagram" src="https://user-images.githubusercontent.com/107724456/177056930-37ab7dba-a421-4967-9c04-94bb66b4ece9.png">

To store data, I used SQLite.
As Field, I prepared 4 field: "id", "food_Name", "food_quantity", "expiry_date", "user_id".<br/>


This is the database structure.
```
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_QUANTITY + " REAL, " +
                        COLUMN_EXPIRY_DATE + " TEXT, user_id INTEGER);  ";
        db.execSQL(query); 
    }
```
you cand find more in [DatabaseHelper].<br/>

