# Data Stores Design

## MySQL Database Design

### Table: admins
- id: INT, Primary Key, Auto Increment
- email: TINYTEXT, Not Null, Unique
- password: TINYTEXT, Not Null

### Table: doctors
- id: INT, Primary Key, Auto Increment
- email: TINYTEXT, Not Null, Unique
- password: TINYTEXT, Not Null
- name: TINYTEXT, Not Null
- specialization: TINYTEXT, Not Null
- contact: TINYTEXT, Not Null

### Table: patients
- id: INT, Primary Key, Auto Increment
- email: TINYTEXT, Not Null, Unique
- password: TINYTEXT, Not Null
- name: TINYTEXT, Not Null

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

## MongoDB Collection Design

### Collection: logs

Record example:

```json
{
  "_id": "ObjectId('64abc123456')",
  "logtime": "2025-02-03T17:23:17.832Z",
  "level": "info",
  "tags" : [ "info" , "settings" ],
  "logger": "com.example.MyApplication",
  "context": [
    "User is connecting",
    "The controller starts his session"
  ],
  "message": {
    "text" : "The file $1 is not missing",
    "parameters": [ "settings.json" ]
  },
}
