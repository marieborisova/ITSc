package com.example.textquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Character manager; // персонаж
    Story story; // история (сюжет)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new Character("Вася");
        story = new Story();
        // в первый раз выводим на форму весь необходимый текст и элементы
        // управления
        updateStatus();
    }

    private void go(int i) {
        story.go(i + 1);
        updateStatus();
        // если история закончилась, выводим на экран поздравление
        if (story.isEnd())
            Toast.makeText(this, "Игра закончена!", Toast.LENGTH_LONG).show();
    }

    // в этом методе размещаем всю информацию, специфичную для текущей
    // ситуации на форме приложения, а также размещаем кнопки, которые
    // позволят пользователю выбрать дальнейший ход событий
    private void updateStatus() {
        // не забываем обновить репутацию в соответствии с новым
        // состоянием дел
        manager.A += story.current_situation.dA;
        manager.K += story.current_situation.dK;
        manager.R += story.current_situation.dR;
        // выводим статус на форму
        String s = "Карьера:" + manager.K +"\nАктивы:" + manager.A + "\nРепутация:" + manager.R;
        ((TextView) findViewById(R.id.Status)).setText(s);
        // аналогично для заголовка и описания ситуации
        ((TextView) findViewById(R.id.Title)).setText(story.current_situation.subject); //story.current_situation.subject
        ((TextView) findViewById(R.id.Situation)).setText(story.current_situation.text);//story.current_situation.text
        //((LinearLayout) findViewById(R.id.layout)).removeAllViews();
        // размещаем кнопку для каждого варианта, который пользователь
        // может выбрать

        for (int i = 0; i < story.current_situation.direction.length; i++) {
            Button b = new Button(this);
            b.setText(Integer.toString(i + 1));//i + 1
            final int buttonId = i;//i
            // Внимание! в анонимных классах
            // можно использовать только те переменные метода,
            // которые объявлены как final.
            // Создаем объект анонимного класса и устанавливаем его
            // обработчиком нажатия на кнопку
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go(buttonId);
                    // поскольку анонимный класс имеет полный
                    // доступ к методам и переменным родительского,
                    // то просто вызываем нужный нам метод.
                }
            });
            // добавляем готовую кнопку на разметку
            ((LinearLayout) findViewById(R.id.buttonLayout)).addView(b);
        }
    }
}