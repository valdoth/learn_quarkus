def is_valid(assignment, course, teacher, room, timeslot):
    # Vérifier si l'assignation est valide
    # Contraintes spécifiques :
    # 1. Un enseignant ne peut enseigner qu'un seul cours à la fois.
    for other_course, (other_teacher, _, _) in assignment.items():
        if other_teacher == teacher:
            return False
    # 2. Une salle ne peut être utilisée que pour un cours à la fois.
    for other_course, (_, other_room, _) in assignment.items():
        if other_room == room:
            return False
    # 3. Un cours ne peut être enseigné que dans un créneau horaire spécifique.
    # (Cette contrainte est déjà implicite dans la structure de données des créneaux horaires)
    # 4. Un enseignant ne peut enseigner que dans des salles spécifiques.
    # (Cette contrainte peut être ajoutée en fonction des règles spécifiques à votre institution)
    return True

def schedule_courses(courses, teachers, rooms, timeslots, assignment={}, course_index=0):
    if course_index == len(courses):
        # Si tous les cours sont assignés, retourner l'assignation
        return assignment
    
    course = courses[course_index]
    for teacher in teachers:
        for room in rooms:
            for timeslot in timeslots:
                if is_valid(assignment, course, teacher, room, timeslot):
                    # Créer une nouvelle assignation avec le cours actuel
                    new_assignment = assignment.copy()
                    new_assignment[course] = (teacher, room, timeslot)
                    
                    # Essayer de planifier le prochain cours
                    result = schedule_courses(courses, teachers, rooms, timeslots, new_assignment, course_index + 1)
                    
                    if result:
                        # Si une solution est trouvée, retourner cette solution
                        return result
    
    # Si aucune assignation n'est trouvée pour le cours actuel, retourner None
    return None

# Exemple d'utilisation
courses = ['Math', 'Physics', 'Chemistry', 'Biology', 'History', 'Geography', 'Art', 'Music', 'Dance', 'Theatre']
teachers = ['Mr. Smith', 'Mrs. Johnson', 'Dr. Brown', 'Ms. White', 'Prof. Green', 'Ms. Black', 'Mr. Red', 'Ms. Blue', 'Mr. Yellow', 'Ms. Pink']
rooms = ['Room 101', 'Room 102', 'Room 103', 'Room 104', 'Room 105', 'Room 106', 'Room 107', 'Room 108', 'Room 109', 'Room 110']
timeslots = ['9:00-10:00', '10:00-11:00', '11:00-12:00', '12:00-13:00', '13:00-14:00', '14:00-15:00', '15:00-16:00', '16:00-17:00', '17:00-18:00', '18:00-19:00']

solution = schedule_courses(courses, teachers, rooms, timeslots)

if solution:
    for course, (teacher, room, timeslot) in solution.items():
        print(f"{course} est enseigné par {teacher} dans {room} à {timeslot}")
else:
    print("Aucune solution trouvée.")
