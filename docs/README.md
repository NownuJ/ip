# Toki

![Ui](Ui.png)

Toki is a task manager chatbot that helps users keep track of tasks with
features such as reminders, deadlines, and search.

## Quick Start

1. Ensure you have Java 17 installed.

2. Download the latest jar. file from [here](https://github.com/NownuJ/ip/tree/master/release).

3. Run the app: `java -jar toki.jar`.


## Features

### Adding new tasks: `todo`

There are 3 types of tasks you can add to Toki.
- To-do
- Deadlines
- Events

Adds a new To-do task, which only contain its description.

Format: `todo d/DESCRIPTION`

Example: `todo Read book` This creates a new to-do task with `Read book` as description.

### Adding new tasks: `deadline`

Adds a new Deadline task, which has a description, and date it is due by.

Format: `deadline d/DESCRIPTION b/BY`

Example: `deadline Finish assignment 2025-09-28` This creates a new deadline task with `Finish assignment` as description and `2025-09-28` as the date it is due by.

### Adding new tasks: `event`

Adds a new Event task, which has a description, and two dates to indicate from when to when it lasts.

Format: `event d/DESCRIPTION f/FROM t/TO`

Example: `event Recess week 2025-09-22 2025-09-26` This creates a new event task with `Recess week` as description that starts on `2025-09-22` and ends on `2025-09-26`.

### Listing all tasks: List

Shows a list of all tasks in the application.

Format: `list`

### Mark a task as done: mark

Mark a task on the list as done.

Format: `mark i/INDEX`

Example: 
- `mark 1` This will mark the first task on the list as done.
- `mark 3` This will mark the third task on the list as done.

### Mark a task as undone: unmark

Mark a task on the list as undone.

Format: `unmark i/INDEX`

Example:
- `unmark 1` This will mark the first task on the list as undone.
- `unmark 3` This will mark the third task on the list as undone.

### Deleting a task: delete

Deletes a task on the list. 

Format: `delete i/INDEX`

Example:
- `delete 1` This will delete the first task on the list.
- `delete 3` This will delete the third task on the list.

### Locating tasks by keyword: find

Finds all tasks that contain the keyword on their descriptions. 

Format: `find k/KEYWORD`

Example: 
- `Find assignment` This will list all tasks with keyword `assignment` on their description.

### Setting reminder on tasks: `remind`

Sets reminder date on selected task. 

Format: `remind i/INDEX r/REMINDERDATE`

Example: `remind 1 2025-09-29` This will set a reminder with date 2025-09-25 on the first task of the list.

### Delete reminder on tasks: unremind

Deletes reminder on selected task.

Format: `unremind i/INDEX`

Example: `unremind 1` This will delete the reminder on the first task of the list.

### Lists all tasks with reminders: reminders

Shows a list of all tasks with a reminder.

Format: `reminders`

### Exiting the program: bye

Displays the farewell message.

Format: `bye`

### Saving the data

Toki data are saved automatically as a txt file under data/toki.txt.

