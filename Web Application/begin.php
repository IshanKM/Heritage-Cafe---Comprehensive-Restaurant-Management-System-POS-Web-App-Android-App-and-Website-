<?php

  //connection to database

  error_reporting(0);
  session_start();
  $connect = mysqli_connect('localhost','root','','the_haritage_cafe');

  //add to order list code

    if(isset($_POST["add_to_cart"]))
    {
      if(isset($_SESSION["shopping_cart"]))
      {
        $item_array_id = array_column($_SESSION["shopping_cart"], "item_id");
        if(!in_array($_GET["id"], $item_array_id))
        {
          $count = count($_SESSION["shopping_cart"]);
    
    	  //get all item details

          $item_array = array(
                      'item_id'       =>   $_GET["id"],
                      'product_img'     =>   $_POST["hidden_image"],
                      'item_name'     =>   $_POST["hidden_name"],
                      'item_price'    =>   $_POST['hidden_price'],
                      'item_quantity' =>   $_POST["quantity"]

            );
          
            $_SESSION["shopping_cart"][$count] = $item_array;
        }
        
        else

        {
          echo '<script>alert("Item already added ")</script>';
        }
      }

      else
      
      {
        
        //if cart empty (mukuth nattan) 

         $item_array = array(
                      'item_id'       =>   $_GET["id"],                     
                      'product_img'   =>   $_POST["hidden_image"],
                      'item_name'     =>   $_POST["hidden_name"],
                      'item_price'    =>   $_POST['hidden_price'],
                      'item_quantity' =>   $_POST["quantity"]

            );
           $_SESSION["shopping_cart"][0] = $item_array;
      }
    }

	//Remove in cart

    if(isset($_GET["action"]))
    {
      if($_GET["action"] == "delete")
      {
        foreach($_SESSION["shopping_cart"] as $key=>$value)
            {
              if($value["item_id"] == $_GET["id"])
              {
                unset($_SESSION["shopping_cart"][$key]);
                
              }
            }
      }
    }


  ?>
 

<!DOCTYPE html>  
 <html>  
      <head>  
           
           <title>ORDER PAGE</title>
           <link rel="stylesheet" type="text/css" href="css/orderselect.css">
           <script src="js/order.js"> </script>
           <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
           <script src="sweetalert2.min.js"></script>
           <link rel="stylesheet" href="sweetalert2.min.css">

      </head>  
      
      <body>
      		<div class="all">              

      				<div class="topheader" style="height: 65px;border-bottom: 1px solid white;">
               
                  <!-- brightness -->

                  <div class="bright" style="">
        				      <font>Brightness </font> <input type="range" id="range" min="20" max="100" value="100">
     				       </div>

                  <!-- logo -->

                  <div class="logoimage" align="left" style="float: left;">
                    
                      <img src="img/logosample.png" width="220px" height="60px" style="">

                  </div>
                  

                  <!-- clock -->

                  <div id="clock">
                
                  </div>

              </div>
    			   
             <script>
      				    //brightness js
                  rangeInput = document.getElementById('range');
      				    container = document.getElementsByClassName('all')[0];
      				    rangeInput.addEventListener("change",function(){
       				     container.style.filter = "brightness(" + rangeInput.value + "%)";
      				  });
    			   </script>



      			<!-- catogery select -->

      	<div class="header">
    				
    				<input type="checkbox" id="chk">
    				<label for="chk" class="show-menu-btn">
      					<img src="img/listshow.png" width="50px" height="50px">
    				</label>

    			  <ul class="menu">
      				<button onclick="breakfast()">Breakfast</button>
      				<button onclick="pizza()">Pizza</button>
      				<button onclick="pasta()">Pasta</button>
      				<button onclick="soup()">Soup</button>
          	  <button onclick="sandwich()">Sandwichs</button>
          		<button onclick="desert()">Deserts</button>
              <button onclick="drink()">Drinks</button>
      
      				<label for="chk" class="hide-menu-btn">
        			  <img src="img/listshow.png" width="50px" height="50px">
      				</label>
    			  </ul>
 				</div>

      			
      			<!-- cart show area baby -->

      			<div class="total">
      				<div class="tableselect">
      					
      					<!-- table select -->
                
                <form method="post" action="storeorders.php">
      					<font id="tablefont">Select Your Table : </font>
      					<select id="tbleslectarea" name="tbleslectarea" class="tableselectarea">
      						<option>Select</option>
      						<?php 
      							$tableslct = "SELECT * FROM tables WHERE Status = 'available' ORDER BY Table_No  ASC";
                   				$tableresult = mysqli_query($connect,$tableslct);
                    			if(mysqli_num_rows($tableresult) >0)
                    				{
                      					while($row = mysqli_fetch_array($tableresult))
                      				{

                  				?>
                  					<option ><?php echo $row['Table_No'];  ?></option>

      						 <?php }} ?>
      						
      					</select>
      					<br>
      				</div>
      				<div class="addcartdisplay">
      					<table style="width: 350px;margin-left: 30px;" align="center" class="addcarttable" id="addcarttable">  
                            
                           <?php

                           $storedatadb = "";
                           $idstore = "";

                           if(!empty($_SESSION["shopping_cart"]))
                           {
                            	$total = 0;
                            	foreach($_SESSION["shopping_cart"] as $key => $value)
                           			{
                           				$idall = $value['item_id'];
                                  $idstore = $idstore .  $idall . ","; 
                                  $itemnamedb = $value['item_name'];
                           				$itemquantitydb = $itemquantitydb . $value['item_quantity'];
                           				$storedatadb = $storedatadb . "ORDER NAME :- " . $itemnamedb . "" . "" . " , QUANTITY :- " . "" . $value['item_quantity'] . " | " ;
                             		?>
                             			<style>
                             	 			.total
  											               {
    											               background: black;
    										               }

                             			</style>
                          <tr> 
                               
                               <td>
                               		<img src="<?php echo $value['product_img'];?>" style="width: 100px;border-radius: 10px;">
                               </td>
                               
                               <td>
                               		<font color="white"><?php echo $value['item_name'];?><br> 
                               		Rs <?php echo number_format($value["item_quantity"] * $value["item_price"],2);?></font>
                           	   </td> 
                               
                               <td>
                               		<font color="white"><?php echo $value['item_quantity']; ?></font>
                               	</td>     
                                 
                               <td style="padding-left: 20px;">
                               		<a href="begin.php?action=delete&id=<?php  echo $value['item_id'];?>"><img src="img/close.png" width="30px" height="30px"></a>
                               </td>  
                          </tr>  
                          
                          	<?php $total = $total + ($value["item_quantity"] * $value['item_price']);
                          	 
                           	}
                           	?>
 
                          	<?php 
                          }
                          	else
                          		{
                          		  ?> 
                          			<h1 style="text-align: center;margin-top: 100px;color: rgba(255,255,255,0.5);font-size: 50px;">Empty</h1>
                          			<?php
 
                           		}?> 
                           	<?php $Total =$total;?>
                     	</table>  
                	</div>
                			
                			<div class="priceshow">               			   
                			              
                			   <font style="color: white;margin-left:30px;margin-top: 5%; font-size: 25px;font-weight: bold;float: left;">Total </font>

                			   <font style="color: white;margin-right:30px;margin-top: 5%; font-size: 25px;font-weight: bold;float: right;">
                			   Rs <?php echo number_format($Total);?> /=</font>

                			</div>

                      <script>
                        
                           //validate Order Button
                           //get from SweetAlert.com web site

                           function checktable()
                              {
                                var emptytable = document.getElementById("addcarttable").rows.length;
                                
                                if (emptytable == 0)
                                  {
                                    emptyorder();
                                    return false;
                                  }

                                var table = document.getElementById("tbleslectarea").value;
                                if (table == "Select")
                                  {                                    
                                    emptytablemasssage();
                                    document.getElementById("tbleslectarea").style.borderColor = "red";
                                    document.getElementById("tablefont").style.color = "red";                                   
                                    return false;
                                  }
                                
                              }

                          function emptytablemasssage()
                              {

                                  Swal.fire
                                    ({
                                      backdrop: 'rgba(0,0,0,0.8)',
                                      icon: 'info',
                                      title: 'Hi Customer...',
                                      text: 'Please Select Your Table..!',
                                      
                                    });

                              }

                          function emptyorder()
                              {

                                  Swal.fire
                                    ({
                                      backdrop: 'rgba(0,0,0,0.8)',
                                      icon: 'warning',
                                      title: 'Hi Customer...',
                                      text: 'Please Select Your Foods First..!',
                                      
                                    });

                              }


                      </script>

                			<div class="buttonshow" align="center">

                			   		<input type="hidden" name="storedborder" value="<?php echo $storedatadb ?>" />
                            <input type="hidden" name="storeid" value="<?php echo $idstore ?>" />
                            <input type="hidden" name="storeqty" value="<?php echo $itemquantitydb ?>" />
                            <input type="hidden" name="total" value="<?php echo $Total ?>" />

                			   		<button type="submit" name="sendorder" onclick="return checktable()">ORDER</button>

                			   </form>


                			</div>

                </div>       


      			<!-- Database show area -->

      			<!-- breakfast -->
            <div class="alltable">
      			<div class="dbshow1" id="dbshow1">
      			<table>
      				<tr>
           		  
                  	<?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'breakfast' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                    ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                              <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';

                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else if ( $row['Discount'] == 0 )
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php 
                              $total = $row['Price'];
                             echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" 	 min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $total;?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br> 

                <!-- Pizza -->

      			<div class="dbshow2" id="dbshow2" style="visibility: hidden;">
      			<table>
      				<tr>
           		  

                  <?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'pizza' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                               <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';

                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br>

                <!-- Pasta -->

      			<div class="dbshow3" id="dbshow3" style="visibility: hidden;">
      			<table>
      				<tr>
           		  

                  	<?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'pasta' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                               <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';

                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br>

                <!-- soups -->

      			<div class="dbshow4" style="visibility: hidden;" id="dbshow4">
      			<table>
      				<tr>
           		  

                  	<?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'soups' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                              <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';
                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br> 

                <!-- Sandwich -->

      			<div class="dbshow5" style="visibility: hidden;" id="dbshow5">
      			<table>
      				<tr>
           		  

                  	<?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'sandwich' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                               <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';
                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br> 

                <!-- Desert -->

      			<div class="dbshow6" style="visibility: hidden;" id="dbshow6">
      			<table>
      				<tr>
           		  

                  	<?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'deserts' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                  		<td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                               <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';
                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                               		<h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
        						<?php 
          							if ( $row['Discount'] != 0 )
           								{

            							?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp 
               					<?php 
                  							$price = $row['Price'];
                  							$discount = $row['Discount'];
                  							$cal =  $price * ($discount /100 );
                  							$total = $price - $cal;
                  						?>	<font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                  							<font class="fnt">Rs <?php echo $total; ?> /=</font> 
           						<?php } 
           							else
           								{

            							?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
            								&nbsp &nbsp &nbsp &nbsp &nbsp
             								 <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
           						<?php } ?>
        						</div>
                               <div class="quantity&addcart" style="height: 40px;">
                               		<input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                               		<input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                               		<p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                               			<?php echo $row['Description'];  ?>                              				
                               		</p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                           	   <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                  	<?php } } ?>
                  			</td>
                  		</tr>
                  	</table>
                </div>
                <br>

                    <!-- drinks -->

            <div class="dbshow7" style="visibility: hidden;" id="dbshow7">
            <table>
              <tr>
                

                    <?php
                    $qury = "SELECT * FROM `items` WHERE Category = 'drink' AND Availability = 'Available' ORDER BY Price ASC";
                    $result = mysqli_query($connect,$qury);
                    if(mysqli_num_rows($result) >0)
                    {
                      while($row = mysqli_fetch_array($result))
                      {

                  ?>
                      <td>
                     <form method="post" action="begin.php?action=add&id=<?php echo $row["id"];?>">  
                          <div style="border:1px solid #333; background-color:black; border-radius:5px;height: 480px;width: 300px;padding-top: 10px;">  
                               <?php 
                                  echo '<img src ="data:image;base64,'.base64_encode($row['imageoriginal']).' "height="180" width="250" />';
                               ?>
                               <div class="name" style="height: 70px;margin-top: 10px;">
                                  <h4 class="text-info" style="color: white"><?php echo $row['Name'];?></h4> 
                               </div>

                               <div class="Price" style="height: 70px;">
                    <?php 
                        if ( $row['Discount'] != 0 )
                          {

                          ?> <font class="fnt"> <?php echo $row['Discount'];  ?>%</font>
                            &nbsp &nbsp &nbsp &nbsp &nbsp 
                        <?php 
                                $price = $row['Price'];
                                $discount = $row['Discount'];
                                $cal =  $price * ($discount /100 );
                                $total = $price - $cal;
                              ?>  <font class="fnt"> Rs <del>  <?php echo $row['Price']; ?></del> /=</font><br>
                                <font class="fnt">Rs <?php echo $total; ?> /=</font> 
                      <?php } 
                        else
                          {

                          ?><font class="fnt"> <?php echo $row['Discount'] ?>%</font>
                            &nbsp &nbsp &nbsp &nbsp &nbsp
                             <font class="fnt"> Rs <?php echo $row['Price'] ?> /=</font>
                      <?php } ?>
                    </div>
                               <div class="quantity&addcart" style="height: 40px;">
                                  <input type="number" name="quantity" value="1" style="border-radius: 3px;padding: 7px;text-align: center;width: 50px;float: left;margin-left: 20px;background: transparent;;border: 2px solid white;color: white;" min="1" />
                                  <input type="submit" name="add_to_cart" style="float: right;margin-right: 20px;padding: 8px;;border: 2px solid white;background:transparent;color: white;cursor: pointer;" value="Add to Cart">
                               </div>

                               <div class="descri" style="height: 100px;margin-top: 10px;">
                                  <p align="center" style="color: white;padding:10px;font-size:11px;text-align: center;">
                                    <?php echo $row['Description'];  ?>                                     
                                  </p>
                               </div>
                              
                               <input type="hidden" name="hidden_name" value="<?php echo $row['Name'] ?>" />
                               <input type="hidden" name="hidden_image" value="<?php echo $row['Image'];?>">
                               <input type="hidden" name="hidden_price" value="<?php echo $row['Price'];?>">

                                
                          </div>  
                     </form>  
                  
                    <?php } } ?>
                        </td>
                      </tr>
                    </table>
                </div>
                <br> 
        </div>
        </div>
      </body>
 		
 </html>

 <?php mysqli_close($connect); 
      
 ?>