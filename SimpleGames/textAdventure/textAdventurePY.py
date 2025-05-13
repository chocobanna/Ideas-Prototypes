
class Point:
    def __init__(self, x, y, description, option1, option2, option3):
        self.x = x
        self.y = y
        self.description = description
        self.option1 = option1
        self.option2 = option2
        self.option3 = option3
    def __str__(self):
        return f"Point({self.x}, {self.y}, {self.description}, {self.option1}, {self.option2}, {self.option3})"
    def __repr__(self):
        return self.__str__()

class Campaign:
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.points = []
    def add_point(self, point):
        self.points.append(point)
    def __str__(self):
        return f"Campaign({self.name}, {self.description}, {len(self.points)} points)"
    def __repr__(self):
        return self.__str__()
    
class Game:
    

firstCampaign = Campaign("The Lost Treasure", "A quest to find the lost treasure of the ancient civilization.")
firstCampaign.add_point(Point(0, 0, "You are at the entrance of a dark cave.", "Enter the cave", "Look around", "Leave"))
firstCampaign.add_point(Point(1, 0, "You are inside the cave. It's dark and damp.", "Light a torch", "Explore deeper", "Go back"))
firstCampaign.add_point(Point(2, 0, "You find a treasure chest!", "Open the chest", "Leave it alone", "Take it with you"))
firstCampaign.add_point(Point(3, 0, "You are outside the cave. The sun is shining.", "Go back inside", "Look for more treasure", "Leave the area"))