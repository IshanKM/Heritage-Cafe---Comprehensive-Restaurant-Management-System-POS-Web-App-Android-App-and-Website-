<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
		  if (isset($_POST['IMEI_Number']) and isset($_POST['Name']) and isset($_POST['Price']) and isset($_POST['Qty']) and isset($_POST['image'])) 
			{

				 $db = new Dbwrite();
				 $result = $db->addtocart($_POST['IMEI_Number'],$_POST['Name'],$_POST['Price'],$_POST['Qty'],$_POST['image']);
				 if($result == 1)
					{
						$response['error'] = true;
						$response['message'] = "Already Added";
					}
				 else
					{
						$response['error'] = false;
				 		$response['message'] = "Add to Cart Successfully";
					}			 
			}
		  else
			{
				$response['error'] = true;
				$response['message'] = "Please fill all fields";
			}
		} 
	    else 
	    {
			$response['error'] = true;
			$response['message'] = "Invalid Requast";
	    }
	


echo json_encode($response);

