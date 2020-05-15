#!/bin/bash
# set -v

enable_language=("java","rust","scala", "python")
root_path=`pwd`

# auto compile java and scala
`gradle -s build > java_scala_compile.log`

for sub_module in $(ls $root_path)
do
	module_name=(${sub_module//-/ })
	arr_length=${#module_name[*]}
	if [[ -d $sub_module ]] && [[ "$arr_length" -eq 2 ]] ;then
		echo "sub module [ $sub_module ]"
		if [[  $arr_length -eq 2 ]];then
			lang=${module_name[0]}
			project=${module_name[0]}
			is_enable=`echo "${enable_language[@]}" | grep -wq "$lang" &&  echo "Yes" || echo "No"`
			if [[  "$is_enable" = "No" ]];then
				echo "############ [ not enable $lang language in this project ]"
				break
			fi
			# compile rust
			case "$lang" in
			"rust")
			echo "################# [ compile $sub_module ]"
			`cd $sub_module;cargo build >/dev/null 2>&1 ;cd .. `;;
			# compile python
			"python")
			echo "############ [ TODO ]";;
			*)
			echo "| exclude $sub_module";;
			esac
			for cdir in $(ls $sub_module)
			do
				echo "       | file ############ [ $cdir ]"
			done
		fi
	fi
done