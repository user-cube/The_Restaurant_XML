echo "Compressing data to be sent to the waiter side node."
rm -rf waiter.zip
zip -rq waiter.zip Waiter
echo "Transfering data to the waiter side node."
sshpass -f password ssh pc_name 'mkdir -p teste/RestaurantSocketCSFinal'
sshpass -f password ssh pc_name 'rm -rf teste/RestaurantSocketCSFinal/*'
sshpass -f password scp waiter.zip pc_name:teste/RestaurantSocketCSFinal/
echo "Decompressing data sent to the waiter side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal ; unzip -uq waiter.zip'
echo "Compiling program files at the waiter side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Waiter/src ; find . -name "*.java" -print | xargs javac'
echo "End of compiling at the waiter side node."
sleep 1
echo "Executing program at the waiter side node."
sshpass -f password ssh pc_name 'cd teste/RestaurantSocketCSFinal/Waiter/src ; java main.MainWaiter'
echo "waiter  shutdown."
