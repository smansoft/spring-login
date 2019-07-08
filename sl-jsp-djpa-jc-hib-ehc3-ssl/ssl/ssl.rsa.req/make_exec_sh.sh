#!/bin/bash

base_dir="."
parent_dir=".."

dirs_list()
{
	echo "----------------------------------->>"
	###################################
	local curr_dir="$1"
	echo "curr_dir = $curr_dir"
	cd "$curr_dir"
 	
	pwd
	###################################
	for fname in *; do
		echo "fname = $fname"
		if [ -d "$fname" ]; then
			local sub_dir="./$fname"
			echo "sub_dir=$sub_dir"
			dirs_list "$sub_dir" "$parent_dir"
		fi
	done
	###################################
	for fnameSh in ./*.sh; do
		if [ -f "$fnameSh" ]; then
			echo "fnameSh = $fnameSh"
			chmod a+x $fnameSh
		fi
	done
	###################################
	local parent_dir="$2"
	cd "$parent_dir"
	###################################
	echo "-----------------------------------<<"
}

dirs_list "$base_dir" "$parent_dir"
