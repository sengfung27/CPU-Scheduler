import time


def print_procs_at_arrival_time():

    with open("./processes_3.txt") as infile:
        lines = infile.read().split('\n')

    # list of processes (not including the header line).
    process_list = [line.split(',') for line in lines[1:]]

    current_time = 0
    current_proc = process_list.pop(0)

    while process_list:

        if int(current_proc[0]) == current_time:
            print(','.join(current_proc))
            current_proc = process_list.pop(0)

        # Purely optional - delay, just so effect can be seen:
        time.sleep(0.02)
        current_time += 1


if __name__ == '__main__':
    print_procs_at_arrival_time()
