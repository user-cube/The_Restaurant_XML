echo "Compressing data to be sent to the bar side node."
rm -rf bar.zip
zip -rq bar.zip Bar
echo "Transfering data to the bar side node."
sshpass -f password ssh pc_name 'mkdir -p teste/RestaurantSocketCSFinal'
sshpass -f password ssh pc_name 'rm -rf teste/RestaurantSocketCSFinal/*'
sshpass -f password scp bar.zip pc_name:teste/RestaurantSocketCSFinal/
echo "Decompressing data sent to the bar side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal ; unzip -uq bar.zip'
echo "Compiling program files at the bar side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Bar/src ; find . -name "*.java" -print | xargs javac'
echo "End of compiling at the bar side node."
sleep 1
echo "Executing program at the bar side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Bar/src ; java main.MainBar'
echo "Bar  shutdown."
