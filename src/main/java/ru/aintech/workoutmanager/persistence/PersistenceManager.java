package ru.aintech.workoutmanager.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

class PersistenceManager {
    
    private static final PersistenceManager instance = new PersistenceManager();
    
    public static PersistenceManager getInstance() { return instance; }
    
    private static final Map<MuscleGroup, List<Exercise>> exercises = new HashMap<>();
    
    private static final List<Exercise> allExercises = new ArrayList<>();
    
    private static final List<Workout> workouts = new ArrayList<>();
    
    private static final List<WorkoutSchedule> schedules = new ArrayList<>();
    
    static {
        if (exercises.isEmpty()) {
            
            Arrays.asList(MuscleGroup.values()).forEach(group -> exercises.put(group, new ArrayList<>()));
            
            Map<Integer, Exercise> exerciseById = new HashMap<>();
            Map<Integer, Workout> workoutById = new HashMap<>();
            
            try {
                File persist = new File(Thread.currentThread().getContextClassLoader().getResource("data/Persistence.xml").toURI());
                Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(persist);
                Node rootNode = xml.getDocumentElement();
                
                Node exercisesNode = null;
                Node workoutsNode = null;
                Node schedulesNode = null;
                
                for (int i = 0; i < rootNode.getChildNodes().getLength(); i++) {
                    if (rootNode.getChildNodes().item(i).getNodeName().equals("exercises")) {
                        exercisesNode = rootNode.getChildNodes().item(i);
                    } else if (rootNode.getChildNodes().item(i).getNodeName().equals("workouts")) {
                        workoutsNode = rootNode.getChildNodes().item(i);
                    } else if (rootNode.getChildNodes().item(i).getNodeName().equals("schedules")) {
                        schedulesNode = rootNode.getChildNodes().item(i);
                    }
                }
                
                for (int i = 0; i < exercisesNode.getChildNodes().getLength(); i++) {
                    
                    Node node = null;
                    int id = 0;
                    MuscleGroup group = null;
                    String name = null;
                    int weight = 0;
                    int[] repeats = new int[0];
                    String external = null;
                    Exercise exercise = null;

                    Node exerciseNode = exercisesNode.getChildNodes().item(i);
                    if (exerciseNode.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    
                    for (int j = 0; j < exerciseNode.getChildNodes().getLength(); j++) {
                        node = exerciseNode.getChildNodes().item(j);
                        if (node == null || node.getNodeName() == null) {
                            continue;
                        }
                        String nodeVal = node.getTextContent() == null || node.getTextContent().isEmpty()? null: node.getTextContent();
                        if (nodeVal != null) {
                            switch (node.getNodeName()) {
                                case "id": id = Integer.parseInt(nodeVal); break;
                                case "group": group = MuscleGroup.valueOf(nodeVal); break;
                                case "name": name = nodeVal; break;
                                case "weight": weight = Integer.parseInt(nodeVal); break;
                                case "repeats":
                                        String[] vals = nodeVal.split(",");
                                        repeats = new int[vals.length];
                                        for (int a = 0; a < vals.length; a++) { repeats[a] = Integer.parseInt(vals[a]); }
                                        break;
                                case "external": external = node.getTextContent();
                            }
                        }
                    }
                    
                    exercise = new Exercise(id, group, name, weight, repeats, external);
                    if (group != null) {
                        exercises.get(group).add(exercise);
                    }
                    allExercises.add(exercise);
                    exerciseById.put(exercise.getId(), exercise);
                }
                
                for (int i = 0; i < workoutsNode.getChildNodes().getLength(); i++) {
                    
                    Node node = null;
                    int id = 0;
                    String name = null;
                    int[] exerciseIds = null;
                    Exercise[] exercises = null;
                    Workout workout;

                    Node workoutNode = workoutsNode.getChildNodes().item(i);
                    if (workoutNode.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    
                    for (int j = 0; j < workoutNode.getChildNodes().getLength(); j++) {
                        node = workoutNode.getChildNodes().item(j);
                        if (node == null || node.getNodeName() == null) {
                            continue;
                        }
                        String nodeVal = node.getTextContent() == null || node.getTextContent().isEmpty()? null: node.getTextContent();
                        if (nodeVal != null) {
                            switch (node.getNodeName()) {
                                case "id": id = Integer.parseInt(nodeVal); break;
                                case "name": name = nodeVal; break;
                                case "exercises":
                                    String[] vals = nodeVal.split(",");
                                    exerciseIds = new int[vals.length];
                                    for (int a = 0; a < vals.length; a++) { exerciseIds[a] = Integer.parseInt(vals[a]); }
                                    exercises = new Exercise[vals.length];
                                    for (int a = 0; a < vals.length; a++) { exercises[a] = exerciseById.get(exerciseIds[a]).getCopy(); }
                                    break;
                            }
                        }
                    }
                    
                    workout = new Workout(id, name, Arrays.asList(exercises));
                    workoutById.put(id, workout);
                    workouts.add(workout);
                }
                
                for (int i = 0; i < schedulesNode.getChildNodes().getLength(); i++) {
                    
                    Node node = null;
                    int id = 0;
                    String name = null;
                    int[] workoutIds = null;
                    Workout[] workouts = null;
                    
                    Node scheduleNode = schedulesNode.getChildNodes().item(i);
                    if (scheduleNode.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    
                    for (int j = 0; j < scheduleNode.getChildNodes().getLength(); j++) {
                        node = scheduleNode.getChildNodes().item(j);
                        if (node == null || node.getNodeName() == null) {
                            continue;
                        }
                        String nodeVal = node.getTextContent() == null || node.getTextContent().isEmpty()? null: node.getTextContent();
                        if (nodeVal != null) {
                            switch (node.getNodeName()) {
                                case "id": id = Integer.parseInt(nodeVal); break;
                                case "name": name = nodeVal; break;
                                case "workouts":
                                    String[] vals = nodeVal.split(",");
                                    workoutIds = new int[vals.length];
                                    for (int a = 0; a < vals.length; a++) { workoutIds[a] = Integer.parseInt(vals[a]); }
                                    workouts = new Workout[vals.length];
                                    for (int a = 0; a < vals.length; a++) { workouts[a] = workoutById.get(workoutIds[a]).getCopy(); }
                            }
                        }
                    }
                    
                    schedules.add(new WorkoutSchedule(id, name, workouts));
                }
                
                new WorkoutScoreManager().defineWorkoutStatuses();

            } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private PersistenceManager () {}
    
    public List<Workout> getWorkouts () {
        return workouts;
    }

    public List<WorkoutSchedule> getSchedules() {
        return schedules;
    }
}