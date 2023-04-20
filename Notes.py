import sqlite3

# Соединение с базой данных
conn = sqlite3.connect('notes.db')
c = conn.cursor()

# Создание таблицы
c.execute('''CREATE TABLE IF NOT EXISTS notes
             (id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT)''')

# Функция создания новой заметки
def create_note():
    text = input('Введите текст заметки: ')
    c.execute("INSERT INTO notes (text) VALUES (?)", (text,))
    conn.commit()
    print('Заметка успешно создана')

# Функция чтения списка заметок
def read_notes():
    c.execute("SELECT * FROM notes")
    rows = c.fetchall()
    if len(rows) == 0:
        print('Список заметок пуст')
    else:
        print('Список заметок:')
        for row in rows:
            print(f"{row[0]}. {row[1]}")

# Функция редактирования заметки
def edit_note():
    id = input('Введите id заметки для редактирования: ')
    c.execute("SELECT * FROM notes WHERE id=?", (id,))
    row = c.fetchone()
    if row is None:
        print(f'Заметка с id {id} не найдена')
    else:
        text = input(f'Введите новый текст заметки ({row[1]}): ')
        if len(text.strip()) > 0:
            c.execute("UPDATE notes SET text=? WHERE id=?", (text, id))
            conn.commit()
            print('Заметка успешно отредактирована')
        else:
            print('Текст заметки пуст')

# Функция удаления заметки
def delete_note():
    id = input('Введите id заметки для удаления: ')
    c.execute("SELECT * FROM notes WHERE id=?", (id,))
    row = c.fetchone()
    if row is None:
        print(f'Заметка с id {id} не найдена')
    else:
        confirm = input(f"Вы уверены, что хотите удалить заметку '{row[1]}'? (Y/N) ")
        if confirm.upper() == "Y":
            c.execute("DELETE FROM notes WHERE id=?", (id,))
            conn.commit()
            print('Заметка успешно удалена')
        else:
            print('Удаление отменено')

# Главный цикл программы
while True:
    print()
    print('1. Создать заметку')
    print('2. Посмотреть список заметок')
    print('3. Отредактировать заметку')
    print('4. Удалить заметку')
    print('5. Выход')
    choice = input('Выберите действие: ')
    if choice == '1':
        create_note()
    elif choice == '2':
        read_notes()
    elif choice == '3':
        edit_note()
    elif choice == '4':
        delete_note()
    elif choice == '5':
        break
    else:
        print('Неверный выбор')

# Закрытие соединения с базой данных
conn.close()