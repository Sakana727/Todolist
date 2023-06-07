package sg.edu.rp.c346.id22020383.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public EditText editTextTask;
    public Button buttonAddTask;
    public Button buttonDeleteTask;
    public Button buttonClearTasks;
    public ListView listViewTasks;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> taskList;
    public Spinner spinnerTaskOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize views
        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonDeleteTask = findViewById(R.id.buttonDeleteTask);
        buttonClearTasks = findViewById(R.id.buttonClearTasks);
        listViewTasks = findViewById(R.id.listViewTasks);
        spinnerTaskOptions = findViewById(R.id.spinnerTaskOptions);

        // Initialize task list
        taskList = new ArrayList<>();

        // Initialize adapter and set it to the list view
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(adapter);

        // Set spinner adapter
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.task_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskOptions.setAdapter(spinnerAdapter);

        // Set button click listeners
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });

        buttonClearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTasks();
            }
        });

        // Set spinner selection listener
        spinnerTaskOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleSpinnerSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void handleSpinnerSelection(int position) {
        switch (position) {
            case 0: // Add a new task
                enableAddTaskMode();
                break;
            case 1: // Remove a task
                enableRemoveTaskMode();
                break;
        }
    }

    private void enableAddTaskMode() {
        editTextTask.setHint(R.string.hint_add_task);
        buttonAddTask.setEnabled(true);
        buttonDeleteTask.setEnabled(false);
    }

    private void enableRemoveTaskMode() {
        editTextTask.setHint(R.string.hint_remove_task);
        buttonAddTask.setEnabled(false);
        buttonDeleteTask.setEnabled(true);
    }

    private void addTask() {
        String task = editTextTask.getText().toString().trim();
        if (!task.isEmpty()) {
            taskList.add(task);
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
        }
    }

    private void deleteTask() {
        String indexString = editTextTask.getText().toString().trim();
        if (!indexString.isEmpty()) {
            try {
                int index = Integer.parseInt(indexString);
                if (index >= 0 && index < taskList.size()) {
                    taskList.remove(index);
                    adapter.notifyDataSetChanged();
                    editTextTask.setText("");
                } else {
                    Toast.makeText(this, R.string.toast_wrong_index, Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, R.string.toast_wrong_index, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.toast_no_index, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearTasks() {
        taskList.clear();
        adapter.notifyDataSetChanged();
    }
}
