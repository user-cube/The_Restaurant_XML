function run(){
	./serverSideDeployAndRun.sh &
	sleep 20
	./clientSideDeployAndRun.sh &
}

function runAndKill(){
	./serverSideDeployAndRun.sh &
	sleep 20
	./clientSideDeployAndRun.sh &

	sleep 45
	killall xterm
}

function menu(){
	
	echo "[2] - Matar terminais após terminados os processos"
	echo "[1] - Correr e não matar os terminais"
	echo "[0] - exit"
	
	read -p "Número:" seletor
	
	case $seletor in
		
		2)
			runAndKill
			;;
		1)
			run
			;;
		0)
			exit
			;;

		*)
			echo "Inválido"
			menu
			;;
			
	esac
}
menu
