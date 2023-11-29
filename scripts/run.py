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
        # if file ends with .json
        if not f.endswith('.json'):
            continue
        run_test(folder_name, f)

def run_test(folder_name, file_name, depth=3):
    file_path = benchmarkFolder + folder_name + '/' + file_name
    base_cmd = f'ant neoDeep -Ddepth={depth} -Dlearn=true -Dstat=false -Dfile=""  -Dapp={file_path}'
    outputFolder = outputMainFolder + folder_name + '/'
    outputFileName = file_name.split('.')[0] + '.txt'
    outputFile = outputFolder + outputFileName
    if not os.path.exists(outputFolder):
        os.mkdir(outputFolder)
    
    simplifyFolder = outputMainFolder + "Simplification" + '/'
    simplifyFileName = folder_name + "-" + file_name.split('.')[0] + '.txt'
    simplifyFile = simplifyFolder + simplifyFileName
    if not os.path.exists(simplifyFolder):
        os.mkdir(simplifyFolder)

    nestedFolder = outputMainFolder + "Nested" + '/'
    nestedFileName = folder_name + "-" + file_name.split('.')[0] + '.txt'
    nestedFile = nestedFolder + nestedFileName
    if not os.path.exists(nestedFolder):
        os.mkdir(nestedFolder)
    
    # subprocess run
    try:
        print(f'Running {base_cmd}', end='\t')
        res = subprocess.run(base_cmd, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        print(f'Success')
        stdout = res.stdout.decode('utf-8')
        stderr = res.stderr.decode('utf-8')
    except subprocess.CalledProcessError as e:
        print(f'Error')
        stdout = e.stdout.decode('utf-8')
        stderr = e.stderr.decode('utf-8')
    finally:
        with open(outputFile, 'w') as f:
            f.write(stdout)
            f.write(stderr)
        if file_name.split('-')[1].startswith("simplify"):
            with open(simplifyFile, 'w') as f:
                f.write(stdout)
                f.write(stderr)
        elif file_name.split('-')[1].startswith("nested"):
            with open(nestedFile, 'w') as f:
                f.write(stdout)
                f.write(stderr)
        

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
