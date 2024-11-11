<?php 

	require_once '../includes/Dbwrite.php';
	$response = array();

	$connect = mysqli_connect(dbhost(),dbuser(),dbpassword(),dbname());

	if ($_SERVER['REQUEST_METHOD'] == 'POST') 
	 {
	 	if(isset($_POST['IMEI_Number'])) 
		 {
		 	$db = new Dbwrite();
			$result = $db->getaddtocartcount($_POST['IMEI_Number']);
		    $row = mysqli_fetch_array($result);
		    $response['size'] = $row['COUNT(ID)'];

		    $db = new Dbwrite();
			$result = $db->getaddtocart($_POST['IMEI_Number']);
			$i = 0;
			if(mysqli_num_rows($result) >0)
		     {               
				while($row = mysqli_fetch_array($result))
	              { 
					$response['error'] = false;
					$response["name".$i] = $row['Name'];
					$response["price".$i] = $row['Price']; 
					$response["qty".$i] = $row['Qty']; 
					$response["img".$i] = $row['image']; 
					

		            $i++;
	        	 }

			 }
          }
          else
          {
          	$response['error'] = true;
			$response['message'] = "Not Found IMEI Number";
          }
	 }
    else 
	 {
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	 }

echo json_encode($response);
 ?>