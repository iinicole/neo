import os
import sys
import subprocess
import statistics

outputMainFolder = './output/'

def calculate(folder_name):
    times = []
    all_simplified_times = []
    success_simplified_times = []
    num = 0
    def calculate1(folder_name, file_name):
        nonlocal times, all_simplified_times
        file_path = outputMainFolder + folder_name + '/' + file_name
        with open(file_path, 'r') as f:
            lines = f.readlines()
            synthesized = None
            simplified = None
            for line in lines:
                if 'Synthesis time' in line:
                    times.append(float(line.split(" ")[-1]))
                if "Synthesized PROGRAM" in line:
                    synthesized = line.split(": ")[-1]
                if "Simplified PROGRAM" in line:
                    simplified = line.split(": ")[-1]
                if "Simplification time" in line:
                    all_simplified_times.append(float(line.split("=:")[-1]))
            if synthesized is not None and simplified is not None:
                if synthesized == simplified:
                    success_simplified_times.append(all_simplified_times[-1])
            else:
                raise Exception(f"Synthesized or simplified is None: {synthesized}, {simplified} for file {file_path}")
    
    # get all files in the folder
    folder_path = outputMainFolder + folder_name + '/'
    files = os.listdir(folder_path)
    assert os.path.exists(folder_path), f'Folder {folder_path} does not exist'

    # run all files in the folder
    num = 0
    for f in sorted(files):
        # if file ends with .txt
        if not f.endswith('.txt'):
            continue
        calculate1(folder_name, f)
        num += 1
    
    print(f"{folder_name} mean: {statistics.mean(times)} median: {statistics.median(times)} (Total: {sum(times)}, Num: {num})")
    print(f"Number of simplified: {len(success_simplified_times)}")
        

if __name__ == '__main__':
    # folder path is first argument
    if len(sys.argv) < 2:
        print('Please provide folder name')
        sys.exit(1)
    
    folder_name = sys.argv[1]
    
    calculate(folder_name)
