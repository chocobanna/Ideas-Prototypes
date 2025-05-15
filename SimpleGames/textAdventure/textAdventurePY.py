import subprocess
import sys
import os
import shutil

def launch_child():
    script = os.path.abspath("textAdventurePY.py")
    if sys.platform.startswith("win"):
        subprocess.Popen(["start", "cmd", "/k", f"python {script}"], shell=True)
    else:
        terminal = (
            shutil.which("gnome-terminal")
            or shutil.which("konsole")
            or shutil.which("xterm")
        )
        if terminal is None:
            raise RuntimeError("No terminal emulator found")
        if terminal.endswith("xterm"):
            cmd = [
                terminal,
                "-hold",                              # ← keep window open
                "-fa", "Monospace",
                "-fs", "12",
                "-e", f"python3 {script}"
            ]
        else:
            # gnome-terminal or konsole: exec bash to hold after script
            cmd = [
                terminal,
                "--", "bash", "-c",
                f"python3 {script}; exec bash"
            ]
        subprocess.Popen(cmd)
class Item:
    def __init__(self, name, description,value=0, weight=0):
        self.name = name
        self.description = description
        self.value = value
        self.weight = weight

    def __str__(self):
        return f"{self.name}: {self.description} (Value: {self.value}, Weight: {self.weight})"
    def __repr__(self):
        return self.__str__()

class PointOfInterest:
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.marketExports = []
        self.marketImports = []
    
    def add_market_export(self, item, quantity=100): #quantity is yearly addition
        self.marketExports.append([item, quantity])
    
    def add_market_import(self, item, quantity=100): #quantity is yearly addition
        self.marketImports.append([item, quantity])

    def __str__(self):
        exports = ", ".join(f"{qty}× {item.name}" for item, qty in self.marketExports)
        imports = ", ".join(f"{qty}× {item.name}" for item, qty in self.marketImports)
        return (
            f"{self.name}: {self.description}\n"
            f"Market Exports: {exports or 'None'}\n"
            f"Market Imports: {imports or 'None'}"
        )
    
if __name__ == "__main__":
    launch_child()
    #POIs
    earth = PointOfInterest("Earth", "The third planet from the Sun, home to humanity.")
    moon = PointOfInterest("Moon", "Earth's only natural satellite.")

    #Items
    water = Item("Water", "Essential for life, found in rivers and oceans.", 1, 1)
    rations = Item("Rations", "Food supplies for long journeys.", 5, 2)
    oxygen = Item("Oxygen", "Essential for breathing, found in the atmosphere.", 1, 0.5)
    fuel = Item("Fuel", "Used for powering spacecraft.", 10, 5)
    stone = Item("Stone", "A common material found on Earth and the Moon.", 0.5, 1)
    platinum = Item("Platinum", "A precious metal used in various applications.", 100, 0.1)

    #Adding items to POIs
    earth.add_market_export(water, 1000)
    earth.add_market_export(rations, 500)
    earth.add_market_export(oxygen, 10000)
    earth.add_market_export(fuel, 200)
    moon.add_market_import(water, 1000)
    moon.add_market_import(rations, 500)
    moon.add_market_import(oxygen, 10000)
    moon.add_market_import(fuel, 200)
    moon.add_market_export(platinum, 1000)
    earth.add_market_import(platinum, 2000)

    # Print the details of the points of interest
    print(earth)
    print(moon)
    # Print the details of the items
    print()
    print(water)
    print(rations)
    print(oxygen)
    print(fuel)
    print(stone)
    print(platinum)