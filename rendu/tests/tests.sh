#!/bin/bash
echo "Ce programe prend en parametre (entre guillements) : le chemin ver l'executable java, l'option -jar et le chemin vers le jar. Ex :"


echo './tests.sh "/home/rkouere/jdk1.7.0_75/bin/java -jar /home/rkouere/fac/S2/car/CAR-TP3/RMI/dist/RMI.jar"' 
echo ""
echo "========================="
echo "starting tests"

if [ $# -ne 0 ]
then
    for file in *.xml
    do
	echo "dealing with file $file" 
	$1  $file 1 -test > results.txt
	send_actual_result=`grep "Sending" results.txt | wc -l`
	send_expected_result=`sed -n '1p' expectedRes.txt`

	receive_actual_result=`grep "received" results.txt | wc -l`
	receive_expected_result=`sed -n '2p' expectedRes.txt`

	if [ $send_actual_result -eq $send_expected_result ]
	then
	    if [ $receive_actual_result -eq $receive_expected_result ]    
	    then
		echo "test OK avec fichier $file"
	    else
		echo "test failed avec fichier $file"
	    fi
	else
	    echo "test failed"
	fi
    done

else
echo "Ce programe prend un argument. Voire message ci-dessus"
fi



rm results.txt -f
rm expectedRes.txt -f
