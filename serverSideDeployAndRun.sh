xterm  -T "GRI Side" -hold -e "./gri.sh" &
sleep 3
xterm  -T "Bar Side" -hold -e "./bar.sh" &
sleep 10
xterm  -T "Kitchen Side" -hold -e "./kitchen.sh" &
xterm  -T "Table Side" -hold -e "./table.sh" &

