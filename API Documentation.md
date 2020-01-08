# **MySQL REST API**
----
## **URL**


### **/connections**
  
* ***Description:***
Retrieves all Connections entities.

* ***Method:***
  `GET`  
  
---
### **/connections/{id}**
  
* ***Description:***
Retrieves specified Connections entities.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{id}`
   
   > Note: {id} can be both a single connections id (ex. '1') and a range of connections ids (ex. '1-7').
   > For multiple {id} values, they should be separated by commas (ex. '1,3-5,10').

---
### /connections
  
* ***Description:***
Creates new Connections entity.

* ***Method:***
  `POST`

* **Data Params**
~~~
  {    
	"name": "name",
	"hostName": "hostName",
	"port": 0000,
	"databaseName": "databaseName",
	"username": "username",
	"password": "password"
}
~~~

---
### /connections/{id}
  
* ***Description:***
Updates specified entity.

* ***Method:***
  `PUT`
  
*  ***URL Params***
  
   **Required:**
   
   `{id}`
   
   > Note: {id} can be both a single connections id (ex. '1') and a range of connections ids (ex. '1-7').
   > For multiple {id} values, they should be separated by commas (ex. '1,3-5,10').
  
* **Data Params**
~~~
  {
	"name": "name",
	"hostName": "hostName",
	"port": 0000,
	"databaseName": "databaseName",
	"username": "username",
	"password": "password"
}
~~~
---
### /connections/{id}
  
* ***Description:***
Deletes specified Connections entities by their id.

* ***Method:***
  `DELETE`
  
*  ***URL Params***

   **Required:**
 
   `{id}`
   
   > Note: {id} can be both a single connections id (ex. '1') and a range of connections ids (ex. '1-7').
   > For multiple {id} values, they should be separated by commas (ex. '1,3-5,10').
---
### **/databases/{connectionId}/schemas**
  
* ***Description:***
Retrieves list of schemas in the specified database.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   
   >Note: {connectionId} should be a positive real number 
---
### **/databases/{connectionId}/tables**
  
* ***Description:***
Retrieves existing tables in the specified database.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   
   >Note: {connectionId} should be a positive real number 
---
### **/databases/{connectionId}/{tableName}/columns**
  
* ***Description:***
Retrieves existing columns in the specified table of a database.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   `{tableName}`
   
   >Note: {connectionId} should be a positive real number 
---
### **/databases/{connectionId}/{tableName}/tablePreview**
  
* ***Description:***
Retrieves up to five table records as a preview.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   `{tableName}`
   
   >Note: {connectionId} should be a positive real number 
---
### **/databases/{connectionId}/{tableName}/tableStatistics**
  
* ***Description:***
Shows number of records and attributes of the specified table.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   `{tableName}`
   
   >Note: {connectionId} should be a positive real number   
---
### **/databases/{connectionId}/{tableName}/{columnName}/columnStatistics**
  
* ***Description:***
Shows the maximal, minimal and median values of the specified column.

* ***Method:***
  `GET`
  
*  ***URL Params***

   **Required:**
 
   `{connectionId}`
   `{tableName}`
   `{columnName}`
   
   >Note: {connectionId} should be a positive real number 
---

