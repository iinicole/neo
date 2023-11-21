import os
import sys
import subprocess

benchmarkFolder = './problem/'

def run_all_tests(folder_path):
    # get all files in the folder
    files = os.listdir(folder_path)

    # run all files in the folder
    for f in sorted(files):
        run_test(folder_path + f)

def run_test(file_path):
    base_cmd = f'ant neoDeep -Ddepth=3 -Dlearn=true -Dstat=false -Dfile=""  -Dapp={file_path}'
    # subprocess run
    # os.system(base_cmd)
    res = subprocess.run(base_cmd, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if res.returncode != 0:
        print(f'Error running {file_path}')
    else:
        print(f'Success running {file_path}')

if __name__ == '__main__':
    # folder path is first argument
    folder_path = sys.argv[1]

    # run all files in the folder
    folder_path = benchmarkFolder + folder_path + '/'
    run_all_tests(folder_path)
