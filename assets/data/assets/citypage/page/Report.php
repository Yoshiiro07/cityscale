<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>CityReport</title>

<script src="javascript/jquery.js" type="text/javascript"></script>
<script src="javascript/popper.js" type="text/javascript"></script>
<script src="javascript/bootstrap.js" type="text/javascript"></script>


<link rel="stylesheet" href="css/bootstrap.css" />

<style>
	.background {
		background-image:url(imagens/backgroundtop.png);
		background-repeat:repeat-x;
		height:95px;
	}
	
	.footer {
		margin-top: auto;
		width: 100%;
		height:50px;
		color: white;
		background-image:url(imagens/backgroundtop.png);
		background-repeat:repeat-x;
	}
	
	html {
		height: 100%;
		min-height: 100%;
	}
	
	body {
		display: flex;
		flex-direction: column;
		min-height: 100%;
	}
	
	@media (min-width: 768px) {
		.modal-xl {
			width: 90%;
			max-width: 1200px;
		}
	}
	
</style>

</head>

<body>

	

	<div class="background">
		<div class="container">	
			<div class="row">
				<div class="col-lg-5"><img src="imagens/topreport.png" /></div>
			</div>
		</div>
	</div>	
	
	<div class="container">
		<div class="row m-2">
		<div class="col-lg-10">
		<span><b>Report os bugs e relatos de melhorias a baixo e acompanhe o andamento! Obrigado!</b></span>
		</div>
		<div class="col-lg-2"><button type="button" data-toggle="modal" data-target="#modalReport" class="btn btn-secondary">Reportar</button></div>
		</div>		
	</div>
	
	<div class="main-container">
	
	<div class="container">	
		<div class="row mt-4 ml-4 mr-4 mb-2">
			<div class="col-lg-8 text-center alert alert-secondary">Descricao</div>
			<div class="col-lg-2 text-center alert alert-secondary">Tipo</div>
			<div class="col-lg-2 text-center alert alert-secondary">Resolvido</div>
		</div>
	</div>
	
	<div class="container">
	<?php include 'reportconnect.php'; ?>
	</div>
	
	</div>
	
	<div class="footer">
		<div class="text-center mt-1">Cityscale - 2023 - Developed by: Gui Oliveira</div>
	</div>
	
	
	
	<!-- MODAL LOGIN -->
        <div class="container-fluid">
        <div class="modal fade" id="modalReport">
        <div class="modal-dialog modal-xl">
          <div class="modal-content">

              <!-- Modal Header -->
              <div class="modal-header">
                <h4 class="modal-title">Escreva seu Report:</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>

              <!-- Modal body -->
			  <form action="reportsend.php" method="post">
              <div class="modal-body">
                
				  <div class="row m-2">
                    <div class="col-lg-2">Relato:</div>
                  	<div class="col-lg-10"><input class="form-control" maxlength="250" type="text" name="descricao"/></div>
                  </div>
				  
				  <div class="row m-2">
                    <div class="col-lg-2">Tipo:</div>
                  	<div class="col-lg-2"><input class="form-control" maxlength="10" type="text" name="tipo"/></div>
                  </div>
				   
              </div>

              <!-- Modal footer -->	  
				 <div class="modal-footer">
                     <input type="submit" id="btnEnviarReport" class="btn btn-secondary" value="Enviar" />
                 </div>
			  </form>
          </div>
        </div>
    </div>
	</div>
    <!-- MODAL -->
	

</body>
</html>
