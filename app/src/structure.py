import os

def print_structure(root_dir, indent=""):
    try:
        # Get all items in the current directory
        items = os.listdir(root_dir)
    except PermissionError:
        print(f"{indent}[Access Denied]")
        return

    # Separate directories and files to meet your requirement
    dirs = sorted([d for d in items if os.path.isdir(os.path.join(root_dir, d))])
    files = sorted([f for f in items if os.path.isfile(os.path.join(root_dir, f))])

    # Combine them: Directories first, then files
    sorted_items = dirs + files

    for index, item in enumerate(sorted_items):
        path = os.path.join(root_dir, item)
        is_last = (index == len(sorted_items) - 1)
        
        # Formatting characters for a "tree" look
        connector = "└── " if is_last else "├── "
        print(f"{indent}{connector}{item}")

        # If it's a directory, recurse into it
        if os.path.isdir(path):
            extension = "    " if is_last else "│   "
            print_structure(path, indent + extension)

if __name__ == "__main__":
    # "." represents the current directory
    print_structure(".")