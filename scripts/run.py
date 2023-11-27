import os
import sys
import subprocess

benchmarkFolder = './problem/'
outputMainFolder = './output/'

def run_all_tests(folder_name):
    # get all files in the folder
    folder_path = benchmarkFolder + folder_name + '/'
    files = os.listdir(folder_path)

    # run all files in the folder
    for f in sorted(files):
        run_test(folder_name, f)

def run_test(folder_name, file_name, depth=3):
    file_path = benchmarkFolder + folder_name + '/' + file_name
    base_cmd = f'ant neoDeep -Ddepth={depth} -Dlearn=true -Dstat=false -Dfile=""  -Dapp={file_path}'
    outputFolder = outputMainFolder + folder_name + '/'
    outputFileName = file_name.split('.')[0] + '.txt'
    outputFile = outputFolder + outputFileName
    if not os.path.exists(outputFolder):
        os.mkdir(outputFolder)
    
    # subprocess run
    try:
        print(f'Running {base_cmd}', end='\t')
        res = subprocess.run(base_cmd, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        print(f'Success')
        with open(outputFile, 'w') as f:
            f.write(res.stdout.decode('utf-8'))
            f.write(res.stderr.decode('utf-8'))
    except subprocess.CalledProcessError as e:
        print(f'Error')
        with open(outputFile, 'w') as f:
            f.write(e.stdout.decode('utf-8'))
        

if __name__ == '__main__':
    # folder path is first argument
    if len(sys.argv) < 2:
        print('Please provide folder name')
        sys.exit(1)
    
    folder_name = sys.argv[1]
    file_name = None
    if len(sys.argv) == 3:
        file_name = sys.argv[2]

    if not os.path.exists(outputMainFolder):
        os.mkdir(outputMainFolder)

    if file_name:
        # run only one file
        run_test(folder_name, file_name)
    else:
        # run all files in the folder
        run_all_tests(folder_name)
