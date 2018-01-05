testbbclass_do_testclass(){
	echo "i am test bbclass guocheng"
}

addtask do_testclass before do_build

EXPORT_FUNCTIONS do_testclass
