<?php 

	require_once '../includes/Dbwrite.php';
	$response = array();

	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());

	if ($_SERVER['REQUEST_METHOD'] == 'POST') 
	 {
	 	$qury = "SELECT COUNT(id) FROM `items` WHERE Category = 'breakfast' AND Availability = 'Available'";
	    $result = mysqli_query($connect,$qury);
	    $row = mysqli_fetch_array($result);
	    $response['size'] = $row['COUNT(id)'];

	    $qury = "SELECT * FROM `items` WHERE Category = 'breakfast' AND Availability = 'Available' ORDER BY Price ASC";
	    $result = mysqli_query($connect,$qury);
	    $i = 0;
	    if(mysqli_num_rows($result) >0)
	     {               
			while($row = mysqli_fetch_array($result))
              { 
				$response['error'] = false;
				$response["name".$i] = $row['Name'];
				$response["desc".$i] = $row['Description']; 

				$base64 = base64_encode($row['imageoriginal']);
				$response["img".$i] = $base64; 
				$response["price".$i] = $row['Price']; 
				$response["discount".$i] = $row['Discount']; 

	            $i++;
        	 }

		 }
	 }
    else 
	 {
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	 }

echo json_encode($response);
 ?>