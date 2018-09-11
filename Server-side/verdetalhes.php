<?php

//Habilita o charset para poder mostrar acentuacoes
header("Content-Type: text/html; charset=UTF-8");


//Recebe o parametro
$local = $_GET['local'];


//Retorna os dados do local especificado pelo parametro
//Dados em ordem:
//poluicao,transito,alagamento,inundacoes,desmatamento,endereco,numero
switch ($local)
{
    case "Parque da Aclimação":
		echo "Baixa,Livre,Nenhum Risco,Baixo Risco,Zero,Rua Muniz de Souza,1119";
		break;
    case "Parque Buenos Aires":
		echo "Baixa,Livre,Nenhum Risco,Baixo Risco,Baixo,Av. Angélica,s/n";
		break;
    case "Jardim da Luz":
		echo "Baixa,Livre,Baixo Risco,Nenhum Risco,Médio,R. Ribeiro de Lima,s/n";
		break;
    case "Horto Florestal de São Paulo":
		echo "Baixa,Livre,Baixo Risco,Nenhum Risco,Alto,R. do Horto,931";
		break;
    case "Parque Anhanguera":
		echo "Baixa,Livre,Médio Risco,Alto Risco,Zero,Av. Fortunata Tadiello Natucci,1000";
		break;
    case "Parque Estadual da Cantareira":
		echo "Baixa,Médio,Médio Risco,Alto Risco,Baixo,R. do Horto,1799";
		break;
    case "Parque Cidade de Toronto":
		echo "Baixa,Médio,Alto Risco,Médio Risco,Médio,Av. Cardeal Motta,84";
		break;
    case "Parque da Juventude":
		echo "Baixa,Médio,Alto Risco,Médio Risco,Alto,Av. Cruzeiro do Sul,2630";
		break;
    case "Parque Estadual do Jaraguá":
		echo "Baixa,Médio,Nenhum Risco,Alto Risco,Zero,R. Antônio Cardoso Nogueira,539";
		break;
    case "Parque Lions Club Tucuruvi":
		echo "Baixa,Médio,Nenhum Risco,Médio Risco,Baixo,R. Alcindo Bueno de Assis,500";
		break;
    case "Parque São Domingos":
		echo "Baixa,Intenso,Baixo Risco,Alto Risco,Médio,Rua Pedro Sernagiotti,125";
		break;
    case "Parque Jardim Felicidade":
		echo "Baixa,Intenso,Baixo Risco,Nenhum Risco,Alto,R. Laudelino Viêira de Campos,265";
		break;
    case "Parque Rodrigo de Gásperi":
		echo "Baixa,Intenso,Médio Risco,Médio Risco,Zero,Av. Miguel de Castro,321";
		break;
    case "Parque Vila dos Remédios":
		echo "Baixa,Intenso,Médio Risco,Nenhum Risco,Baixo,Carlos Alberto Vanzolini,413";
		break;
    case "Parque Vila Guilherme":
		echo "Baixa,Intenso,Alto Risco,Nenhum Risco,Médio,Av. Nadir Dias de Figueiredo,s/n";
		break;
    case "Parque Ibirapuera":
		echo "Media,Intenso,Baixo Risco,Baixo Risco,Baixo,Av. Pedro Álvares Cabral,s/n";
		break;
    case "Parque da Independência":
		echo "Media,Livre,Alto Risco,Nenhum Risco,Zero,Av. Nazaré,s/n";
		break;
    case "Parque Estadual das Fontes do Ipiranga":
		echo "Media,Livre,Alto Risco,Médio Risco,Baixo,R. Alfenas,269";
		break;
    case "Jardim Botânico de São Paulo":
		echo "Media,Livre,Nenhum Risco,Baixo Risco,Médio,Av. Miguel Estéfano,3031";
		break;
    case "Parque Zoológico de São Paulo":
		echo "Media,Livre,Nenhum Risco,Alto Risco,Alto,Av. Miguel Estefno,4241";
		break;
    case "Parque Estadual da Serra do Mar":
		echo "Media,Livre,Baixo Risco,Baixo Risco,Zero,Av. Caminho do Mar,42";
		break;
    case "Parque Ecológico do Guarapiranga":
		echo "Media,Médio,Baixo Risco,Nenhum Risco,Baixo,Estrada da Riviera,3286";
		break;
    case "Parque Guarapiranga":
		echo "Media,Médio,Médio Risco,Alto Risco,Médio,Av. Guarapiranga,805";
		break;
    case "Parque Santo Dias":
		echo "Media,Médio,Médio Risco,Nenhum Risco,Alto,R. Arroio das Caneleiras,650";
		break;
    case "Parque Severo Gomes":
		echo "Media,Médio,Alto Risco,Alto Risco,Zero,R. Píres de Oliveira,356";
		break;
    case "Parque Lina e Paulo Raia":
		echo "Media,Médio,Alto Risco,Baixo Risco,Baixo,R. Volkswagen,s/n";
		break;
    case "Parque Nabuco":
		echo "Media,Intenso,Alto Risco,Nenhum Risco,Médio,R. Frederico Albuquerque,120";
		break;
    case "Parque Comandante Jacques Cousteau":
		echo "Media,Intenso,Alto Risco,Alto Risco,Alto,R. Catanumi,60";
		break;
    case "Parque da Consciência Negra":
		echo "Media,Intenso,Nenhum Risco,Médio Risco,Zero,R. José Francisco Brandão,320";
		break;
    case "Parque Ecológico do Tietê":
		echo "Media,Intenso,Nenhum Risco,Alto Risco,Baixo,Rodovia Parque,8054";
		break;
    case "Parque do Carmo":
		echo "Media,Intenso,Baixo Risco,Nenhum Risco,Médio,Av. Afonso de Sampaio e Sousa,951";
		break;
    case "Parque do Piqueri":
		echo "Media,Livre,Baixo Risco,Baixo Risco,Alto,R. Tuiuti,515";
		break;
    case "Centro Esportivo Recreativo e Educativo do Trabalhador":
		echo "Media,Livre,Médio Risco,Alto Risco,Baixo,R. Canuto Abreu,s/n";
		break;
    case "Parque Ecológico da Vila Prudente":
		echo "Media,Livre,Alto Risco,Nenhum Risco,Médio,Rua João Pedro Lecor,s/n";
		break;
    case "Parque Santa Amélia":
		echo "Alta,Livre,Alto Risco,Baixo Risco,Alto,Rua Timóteo Correa de Góes,30";
		break;
    case "Parque Chácara das Flores":
		echo "Alta,Médio,Alto Risco,Alto Risco,Zero,Estrada Dom João Nery,3551";
		break;
    case "Parque Chico Mendes":
		echo "Alta,Médio,Alto Risco,Nenhum Risco,Baixo,R. Lázaro Suave,15";
		break;
    case "Parque Raul Seixas":
		echo "Alta,Médio,Alto Risco,Médio Risco,Médio,R. Murmúrios da Tarde,211";
		break;
    case "Parque Trianon":
		echo "Alta,Médio,Nenhum Risco,Nenhum Risco,Alto,Rua Peixoto Gomide,949";
		break;
    case "Parque Estadual Villa-Lobos":
		echo "Alta,Médio,Nenhum Risco,Baixo Risco,Zero,Av. Prof. Fonseca Rodrigues,2001";
		break;
    case "Parque do Povo":
		echo "Alta,Médio,Baixo Risco,Alto Risco,Baixo,Av. Cidade Jardim,s/n";
		break;
    case "Parque Luís Carlos Prestes":
		echo "Alta,Intenso,Baixo Risco,Nenhum Risco,Médio,Rua João Della Manna,665";
		break;
    case "Parque Previdência":
		echo "Alta,Intenso,Médio Risco,Baixo Risco,Alto,Rua Pedro Peccinini,88";
		break;
    case "Parque Burle Marx":
		echo "Alta,Intenso,Médio Risco,Médio Risco,Zero,Avenida Dona Helena Pereira de Moraes,s/n";
		break;
    case "Parque da Água Branca":
		echo "Alta,Intenso,Medio Risco,Baixo Risco,Baixo,Av. Francisco Matarazzo,455";
		break;
    case "Parque Alfredo Volpi":
		echo "Alta,Intenso,Alto Risco,Nenhum Risco,Médio,Rua Engenheiro Oscar Americano,480";
		break;
    case "Parque Raposo Tavares":
		echo "Alta,Livre,Alto Risco,Alto Risco,Alto,R. Telmo Coelho Filho,200";
		break;
    case "Parque dos Eucaliptos":
		echo "Alta,Livre,Nenhum Risco,Alto Risco,Zero,R. Min. Guimarães,280";
		break;
    case "Parque Chácara do Jockey":
		echo "Alta,Livre,Nenhum Risco,Nenhum Risco,Baixo,Av. Prof. Francisco Morato,5257";
		break;
}

?>