import os
import sys
import subprocess

outputMainFolder = './output/'

def calculate(folder_name):
    # get all files in the folder
    folder_path = outputMainFolder + folder_name + '/'
    files = os.listdir(folder_path)

    # run all files in the folder
    num = 0
    total = 0
    for f in sorted(files):
        # if file ends with .txt
        if not f.endswith('.txt'):
            continue
        total += calculate1(folder_name, f)
        num += 1
    print(f"Average time for {folder_name} is {total/num} (Total: {total}, Num: {num})")

def calculate1(folder_name, file_name):
    file_path = outputMainFolder + folder_name + '/' + file_name
    with open(file_path, 'r') as f:
        lines = f.readlines()
        for line in lines:
            if 'Synthesis time' in line:
                total = float(line.split(" ")[-1])
                # print(total)
                return total
        

if __name__ == '__main__':
    # folder path is first argument
    if len(sys.argv) < 2:
        print('Please provide folder name')
        sys.exit(1)
    
    folder_name = sys.argv[1]
    
    calculate(folder_name)
