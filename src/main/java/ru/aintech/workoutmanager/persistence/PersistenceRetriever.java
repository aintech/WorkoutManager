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

class PersistenceRetriever {
    
    private static final PersistenceRetriever instance = new PersistenceRetriever();
    
    public static PersistenceRetriever getInstance() { return instance; }
    
    private static final Map<MuscleGroup, List<Exercise>> exercises = new HashMap<>();
    
    private static final List<Exercise> allExercises = new ArrayList<>();
    
    private static final List<Workout> workouts = new ArrayList<>();
    
    static {
        if (exercises.isEmpty()) {
            
            Arrays.asList(MuscleGroup.values()).forEach(group -> exercises.put(group, new ArrayList<>()));
            
            Map<Integer, Exercise> exercisesById = new HashMap<>();
            
            try {
                File persist = new File(Thread.currentThread().getContextClassLoader().getResource("data/Persistence.xml").toURI());
                Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(persist);
                Node rootNode = xml.getDocumentElement();
                
                Node exercisesNode = null;
                Node workoutsNode = null;
                Node scheduleNode = null;
                
                for (int i = 0; i < rootNode.getChildNodes().getLength(); i++) {
                    if (rootNode.getChildNodes().item(i).getNodeName().equals("exercises")) {
                        exercisesNode = rootNode.getChildNodes().item(i);
                    } else if (rootNode.getChildNodes().item(i).getNodeName().equals("workouts")) {
                        workoutsNode = rootNode.getChildNodes().item(i);
                    } else if (rootNode.getChildNodes().item(i).getNodeName().equals("schedule")) {
                        scheduleNode = rootNode.getChildNodes().item(i);
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
                    exercisesById.put(exercise.getId(), exercise);
                }
                
                for (int i = 0; i < workoutsNode.getChildNodes().getLength(); i++) {
                    
                    Node node = null;
                    int id = 0;
                    String name = null;
                    int[] exerciseIds = null;
                    Exercise[] exercises = null;

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
                                    for (int a = 0; a < vals.length; a++) { exercises[a] = exercisesById.get(exerciseIds[a]).getCopy(); }
                                    break;
                            }
                        }
                    }
                    
                    workouts.add(new Workout(id, name, exercises));
                }
                
                String schedule = scheduleNode.getTextContent();
                
            } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private PersistenceRetriever () {}

    public Map<MuscleGroup, List<Exercise>> getExercises() {
        return exercises;
    }

    public List<Exercise> getAllExercises() {
        return allExercises;
    }
    
    public List<Workout> getWorkouts () {
        return workouts;
    }
}