# JsonImageAndTextParsing

connection.php

<?php

$dbHost = "localhost";
$dbUser = "root";
$dbPass = "";
$dbName = "ecommerce";  //Your database name, here my database name ecommerce

$connection = mysqli_connect($dbHost,$dbUser,$dbPass,$dbName);


?>

getAllProducts.php


<?php

   include './connection.php';
   // Is this function have any error
   
   if(!$connection){
       
       die("Database connection failed;".mysqli_connect_error()."(".mysqli_connect_errno().")");
       
   } else {
  
		//SQL query to fetch data of a range 
		$sql = "SELECT * from addproduct order by pid desc";

		//Getting result 
		$result = mysqli_query($connection,$sql); 
		
		//Adding results to an array 
		$res = array();  

		while($row = mysqli_fetch_array($result)){
			array_push($res, array(
			    "image"=>$row['image'],
     			"title"=>$row['title'],
				"subtitle"=>$row['subtitle'],
				"price"=>$row['price'],
				"description"=>$row['description'])
				);
		}
		//Displaying the array in json format 
		echo json_encode(array("result"=>$res));
		
	}
	
	mysqli_close($connection);
	
?>

My JSON Data 

{
  "result": [
    {
      "photo": "http:\\192.168.56.1\Android\MyTest\uploads\1.png",
      "name": "Salma"
    },
    {
      "photo": "http:\\192.168.56.1\Android\MyTest\uploads\2.png",
      "name": "Sagor"
    },
    {
      "photo": "http:\\192.168.56.1\Android\MyTest\uploads\3.png",
      "name": "Juyel"
    },
    {
      "photo": "http:\\192.168.56.1\Android\MyTest\uploads\4.png",
      "name": "Rafia"
    }
  ]
}
