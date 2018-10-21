echo "Compressing data to be sent to the chef side node."
rm -rf chef.zip
zip -rq chef.zip Chef
echo "Transfering data to the chef side node."
sshpass -f password ssh pc_name 'mkdir -p teste/RestaurantSocketCSFinal'
sshpass -f password ssh pc_name 'rm -rf teste/RestaurantSocketCSFinal/*'
sshpass -f password scp chef.zip pc_name:teste/RestaurantSocketCSFinal/
echo "Decompressing data sent to the chef side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal ; unzip -uq chef.zip'
echo "Compiling program files at the chef side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Chef/src ; find . -name "*.java" -print | xargs javac'
echo "End of compiling at the chef side node."
sleep 1
echo "Executing program at the chef side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Chef/src ; java main.MainChef'
echo "chef  shutdown."
