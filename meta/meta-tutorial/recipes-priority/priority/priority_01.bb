DESCRIPTION = "I am the priority 1"
PR = "r1"
do_priority () {
	  echo "bblayers position 1 ,my priority is 10"
}

addtask priority before do_build
