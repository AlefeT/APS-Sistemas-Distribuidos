<?php

//Habilita o charset para poder mostrar acentuacoes
header("Content-Type: text/html; charset=UTF-8");


//Recebe parametros
$local = $_GET['local'];
$poluicao = $_GET['poluicao'];
$transito = $_GET['transito'];
$alagamento = $_GET['alagamento'];
$inundacoes = $_GET['inundacoes'];
$desmatamento = $_GET['desmatamento'];


//Caso o local tenha sido informado
if($local != "")
{   
    #Retorna o proprio local
    echo $local;
}
//Caso contrario
else
{
    //Faz um string com todos os locais que atendem os filtros informados
    $str = "";

    //Caso tenham informado Poluicao do Ar
    if($poluicao != "Selecione")
    {
        switch ($poluicao)
        {
            case "Baixa";
                $str = $str . "Parque da Aclimação,Parque Buenos Aires,Jardim da Luz,Horto Florestal de São Paulo,Parque Anhanguera,Parque Estadual da Cantareira,Parque Cidade de Toronto,Parque da Juventude,Parque Estadual do Jaraguá,Parque Lions Club Tucuruvi,Parque São Domingos,Parque Jardim Felicidade,Parque Rodrigo de Gásperi,Parque Vila dos Remédios,Parque Vila Guilherme,";
            break;
            
            case "Media";
                $str = $str . "Parque Ibirapuera,Parque Estadual das Fontes do Ipiranga,Jardim Botânico de São Paulo,Parque Zoológico de São Paulo,Parque Estadual da Serra do Mar,Parque Ecológico do Guarapiranga,Parque Guarapiranga,Parque Santo Dias,Parque Severo Gomes,Parque Lina e Paulo Raia,Parque Nabuco,Parque Comandante Jacques Cousteau,Parque da Consciência Negra,Parque Ecológico do Tietê,Parque do Carmo,Parque do Piqueri,Centro Esportivo Recreativo e Educativo do Trabalhador,Parque Ecológico da Vila Prudente,Parque da Independência,";
            break;
            
            case "Alta";
                $str = $str . "Parque Santa Amélia,Parque Chácara das Flores,Parque Chico Mendes,Parque Raul Seixas,Parque Trianon,Parque Estadual Villa-Lobos,Parque do Povo,Parque Luís Carlos Prestes,Parque Previdência,Parque Burle Marx,Parque da Água Branca,Parque Alfredo Volpi,Parque Raposo Tavares,Parque dos Eucaliptos,Parque Chácara do Jockey,";
            break;
        }
    }

    //Caso tenham informado risco de Transito
    if($transito != "Selecione")
    {
        switch ($transito)
        {
            case "Livre";
                $str = $str . "Parque da Aclimação,Parque Buenos Aires,Jardim da Luz,Horto Florestal de São Paulo,Parque Anhanguera,Parque Estadual das Fontes do Ipiranga,Jardim Botânico de São Paulo,Parque Zoológico de São Paulo,Parque Estadual da Serra do Mar,Parque do Piqueri,Centro Esportivo Recreativo e Educativo do Trabalhador,Parque Ecológico da Vila Prudente,Parque Santa Amélia,Parque Raposo Tavares,Parque dos Eucaliptos,Parque Chácara do Jockey,Parque da Independência,";
            break;
            
            case "Medio";
                $str = $str . "Parque Estadual da Cantareira,Parque Cidade de Toronto,Parque da Juventude,Parque Estadual do Jaraguá,Parque Lions Club Tucuruvi,Parque Ecológico do Guarapiranga,Parque Guarapiranga,Parque Santo Dias,Parque Severo Gomes,Parque Lina e Paulo Raia,Parque Chácara das Flores,Parque Chico Mendes,Parque Raul Seixas,Parque Trianon,Parque Estadual Villa-Lobos,Parque do Povo,";
            break;
            
            case "Intenso";
                $str = $str . "Parque São Domingos,Parque Jardim Felicidade,Parque Rodrigo de Gásperi,Parque Vila dos Remédios,Parque Vila Guilherme,Parque Ibirapuera,Parque Nabuco,Parque Comandante Jacques Cousteau,Parque da Consciência Negra,Parque Ecológico do Tietê,Parque do Carmo,Parque Luís Carlos Prestes,Parque Previdência,Parque Burle Marx,Parque da Água Branca,Parque Alfredo Volpi,";
            break;
        }
    }

    //Caso tenham informado risco de Alagamento
    if($alagamento != "Selecione")
    {
        switch ($alagamento)
        {
            case "Nenhum Risco";
                $str = $str . "Parque da Aclimação,Parque Buenos Aires,Parque Estadual do Jaraguá,Parque Lions Club Tucuruvi,Jardim Botânico de São Paulo,Parque Zoológico de São Paulo,Parque da Consciência Negra,Parque Ecológico do Tietê,Parque Trianon,Parque Estadual Villa-Lobos,Parque dos Eucaliptos,Parque Chácara do Jockey,";
            break;
            
            case "Baixo Risco";
                $str = $str . "Jardim da Luz,Horto Florestal de São Paulo,Parque São Domingos,Parque Jardim Felicidade,Parque Estadual da Serra do Mar,Parque Ecológico do Guarapiranga,Parque do Carmo,Parque do Piqueri,Parque do Povo,Parque Luís Carlos Prestes,Parque Ibirapuera,";
            break;
            
            case "Medio Risco";
                $str = $str . "Parque Anhanguera,Parque Estadual da Cantareira,Parque Rodrigo de Gásperi,Parque Vila dos Remédios,Parque Guarapiranga,Parque Santo Dias,Centro Esportivo Recreativo e Educativo do Trabalhador,Parque Previdência,Parque Burle Marx,Parque da Água Branca,";
            break;
            
            case "Alto Risco";
                $str = $str . "Parque Cidade de Toronto,Parque da Juventude,Parque Vila Guilherme,Parque Estadual das Fontes do Ipiranga,Parque Severo Gomes,Parque Lina e Paulo Raia,Parque Nabuco,Parque Comandante Jacques Cousteau,Parque Ecológico da Vila Prudente,Parque Santa Amélia,Parque Chácara das Flores,Parque Chico Mendes,Parque Raul Seixas,Parque Alfredo Volpi,Parque Raposo Tavares,Parque da Independência,";
            break;
        }
    }

    //Caso tenham informado risco de Inundacao
    if($inundacoes != "Selecione")
    {
        switch ($inundacoes)
        {
            case "Nenhum Risco";
                $str = $str . "Jardim da Luz,Horto Florestal de São Paulo,Parque Jardim Felicidade,Parque Vila dos Remédios,Parque Vila Guilherme,Parque Ecológico do Guarapiranga,Parque Santo Dias,Parque Nabuco,Parque do Carmo,Parque Ecológico da Vila Prudente,Parque Chico Mendes,Parque Trianon,Parque Luís Carlos Prestes,Parque Alfredo Volpi,Parque Chácara do Jockey,Parque da Independência,";
            break;
            
            case "Baixo Risco";
                $str = $str . "Parque da Aclimação,Parque Buenos Aires,Parque Ibirapuera,Jardim Botânico de São Paulo,Parque Estadual da Serra do Mar,Parque Lina e Paulo Raia,Parque do Piqueri,Parque Santa Amélia,Parque Estadual Villa-Lobos,Parque Previdência,Parque da Água Branca,";
            break;
            
            case "Medio Risco";
                $str = $str . "Parque Cidade de Toronto,Parque da Juventude,Parque Lions Club Tucuruvi,Parque Rodrigo de Gásperi,Parque Estadual das Fontes do Ipiranga,Parque da Consciência Negra,Parque Raul Seixas,Parque Burle Marx,";
            break;
            
            case "Alto Risco";
                $str = $str . "Parque Anhanguera,Parque Estadual da Cantareira,Parque Estadual do Jaraguá,Parque São Domingos,Parque Zoológico de São Paulo,Parque Guarapiranga,Parque Severo Gomes,Parque Comandante Jacques Cousteau,Parque Ecológico do Tietê,Centro Esportivo Recreativo e Educativo do Trabalhador,Parque Chácara das Flores,Parque do Povo,Parque Raposo Tavares,Parque dos Eucaliptos,";
            break;
        }
    }

    //Caso tenham informado nivel de Desmatamento
    if($desmatamento != "Selecione")
    {
        switch ($desmatamento)
        {
            case "Zero";
                $str = $str . "Parque da Aclimação,Parque Anhanguera,Parque Estadual do Jaraguá,Parque Rodrigo de Gásperi,Parque Estadual da Serra do Mar,Parque Severo Gomes,Parque da Consciência Negra,Parque Chácara das Flores,Parque Estadual Villa-Lobos,Parque Burle Marx,Parque dos Eucaliptos,Parque da Independência,";
            break;
            
            case "Baixo";
                $str = $str . "Parque Buenos Aires,Parque Estadual da Cantareira,Parque Lions Club Tucuruvi,Parque Vila dos Remédios,Parque Estadual das Fontes do Ipiranga,Parque Ecológico do Guarapiranga,Parque Lina e Paulo Raia,Parque Ecológico do Tietê,Centro Esportivo Recreativo e Educativo do Trabalhador,Parque Chico Mendes,Parque do Povo,Parque da Água Branca,Parque Chácara do Jockey,Parque Ibirapuera,";
            break;
            
            case "Medio";
                $str = $str . "Jardim da Luz,Parque Cidade de Toronto,Parque São Domingos,Parque Vila Guilherme,Jardim Botânico de São Paulo,Parque Guarapiranga,Parque Nabuco,Parque do Carmo,Parque Ecológico da Vila Prudente,Parque Raul Seixas,Parque Luís Carlos Prestes,Parque Alfredo Volpi,";
            break;
            
            case "Alto";
                $str = $str . "Horto Florestal de São Paulo,Parque da Juventude,Parque Jardim Felicidade,Parque Zoológico de São Paulo,Parque Santo Dias,Parque Comandante Jacques Cousteau,Parque do Piqueri,Parque Santa Amélia,Parque Trianon,Parque Previdência,Parque Raposo Tavares,";
            break;
        }
    }


    //Retira os locais repetidos da string
    $str = implode(',',array_unique(explode(',', $str)));

    
    //Retorna a string como resposta
    echo $str;
    
}



?>