echo "Compressing data to be sent to the logger side node."
rm -rf gri.zip
zip -rq gri.zip GRI
echo "Transfering data to the logger side node."
sshpass -f password ssh pc_name 'mkdir -p teste/RestaurantSocketCSFinal'
sshpass -f password ssh pc_name 'rm -rf teste/RestaurantSocketCSFinal/*'
sshpass -f password scp gri.zip pc_name:teste/RestaurantSocketCSFinal/
echo "Decompressing data sent to the logger side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal ; unzip -uq gri.zip'
echo "Compiling program files at the logger side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/GRI/src ; find . -name "*.java" -print | xargs javac'
echo "End of compiling at the logger side node."
sleep 1
echo "Executing program at the logger side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/GRI/src ; java main.MainGRI'
echo "Pull log file"
scp -r pc_name:teste/RestaurantSocketCSFinal/GRI/src/TheRestaurant-CD.txt .
echo "logger  shutdown."
