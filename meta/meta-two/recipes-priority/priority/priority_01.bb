DESCRIPTION = "I am the priority 2"
PR = "r1"
do_priority () {
	  echo "bblayers positon 2 ,my priority is 1"
}

addtask priority before do_build
