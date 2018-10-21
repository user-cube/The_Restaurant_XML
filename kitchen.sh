echo "Compressing data to be sent to the kitchen side node."
rm -rf kitchen.zip
zip -rq kitchen.zip Kitchen
echo "Transfering data to the kitchen side node."
sshpass -f password ssh pc_name 'mkdir -p teste/RestaurantSocketCSFinal'
sshpass -f password ssh pc_name 'rm -rf teste/RestaurantSocketCSFinal/*'
sshpass -f password scp kitchen.zip pc_name:teste/RestaurantSocketCSFinal/
echo "Decompressing data sent to the kitchen side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal ; unzip -uq kitchen.zip'
echo "Compiling program files at the kitchen side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Kitchen/src ; find . -name "*.java" -print | xargs javac'
echo "End of compiling at the kitchen side node."
sleep 1
echo "Executing program at the kitchen side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Kitchen/src ; java main.MainKitchen'
echo "kitchen  shutdown."
