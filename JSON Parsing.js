var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1eK8q5ZSmKVckOCGsPKF8C4mgrjkrwD_zqejCQnwBUbE/edit#gid=0");

var sheet = ss.getSheetByName('List'); // be very careful ... it is the sheet name .. so it should match 


function doPost(e){
var action = e.parameter.action;

if(action == 'addItem'){
  return addItem(e);

}

}





function addItem(e){

var date =  new Date();

var Name = e.parameter.name;

var Employee_code = e.parameter.emp_code;

var Status = e.parameter.status;

sheet.appendRow([date,Name,Employee_code,Status]);

   return ContentService.createTextOutput("Success").setMimeType(ContentService.MimeType.TEXT);



}
