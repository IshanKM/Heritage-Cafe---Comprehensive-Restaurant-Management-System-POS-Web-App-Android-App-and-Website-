<?php 
	
	require_once '../includes/Dbwrite.php';

	$response = array();

		if ($_SERVER['REQUEST_METHOD'] == 'POST') 
		{
			if (isset($_POST['cid']) and isset($_POST['cname']) and isset($_POST['caddress']) and isset($_POST['cmobilenumber']) and isset($_POST['cemail']) and isset($_POST['orderids']) and isset($_POST['orderqtys'])
				and isset($_POST['total']) and isset($_POST['Delivery_Charge'])) 
			{
				$db = new Dbwrite();
				$result = $db->storeonlineorders($_POST['cid'],$_POST['cname'],$_POST['caddress'],$_POST['cmobilenumber'],$_POST['cemail'],$_POST['total'],$_POST['orderids'],$_POST['orderqtys'],$_POST['Delivery_Charge']);
				
				if($result)
				 {
					$response['error'] = false;
					$response['message'] = "Order Successfully";
				 }
				else
				{
					$response['error'] = true;
					$response['message'] = "Connection Problem.Check Your Connection";
				}
				

			}
			else
			{
				$response['error'] = true;
				$response['message'] = "Some error.please try Again";
			}
		} 
	else 
	{
		$response['error'] = true;
		$response['message'] = "Invalid Requast";
	}
	


echo json_encode($response);


