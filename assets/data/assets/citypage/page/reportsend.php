<?php
$servername = "cityserver.mysql.uhserver.com";
$username = "citymaster";
$password = "City@key90";
$dbname = "cityserver";

$descricao = $_POST['descricao'];
$tipo = $_POST['tipo'];
$lSituacao = "Pendente";



// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Falha na conexao: " . $conn->connect_error);
}

$sql = "INSERT INTO Reports (Descricao, Tipo, Situacao)
VALUES ('$descricao', '$tipo', '$lSituacao')";

if ($conn->query($sql) === TRUE) {
  echo "Gravado com sucesso";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();

header("Location: Report.php");
exit;

?>