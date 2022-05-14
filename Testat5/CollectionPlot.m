%% Allgemeines
X = [10^2, 10^3, 10^4, 10^5, 10^6];

%% Plots
% Anmerkung:
% Testläufe, die wohl sehr lange gedauert hätten, wurden abgebrochen und die Messwerte durch "Inf" ersetzt. Sie werden in den Plots nicht dargestellt.

% Plot für add()
addvals = [404000,636000,737000; 2101000, 326000, 5037000; 7579000, 738000, 8705000; 14825000, 14872000, 16300000; 238141000, 103299000, 261689000];
loglog(X, addvals);
legend({"LinkedList", "ArrayList", "HashSet"});
title("Zeit zum Einfügen von n Elementen");
xlabel("n");
ylabel("Zeit in Nanosekunden");
grid on;

print -djpg AddDiagram.jpg

% Plot für remove()
removevals = [336000, 360811, 514000; 518000, 1053000, 1035000; 4432000, 51643000, 930482; 6662000, 5736548000, 12686000; 99717000, Inf, 53553000];
loglog(X, removevals);
legend({"LinkedList", "ArrayList", "HashSet"});
title("Zeit zum Löschen von n Elementen");
xlabel("n");
ylabel("Zeit in Nanosekunden");
grid on;

print -djpg RemoveDiagram.jpg

% Plot für contains()
containsvals = [150000, 129800, 37095; 2438000, 4349000, 86000; 12680000, 18535000, 233000; 2092007000, 1019956000, 1684000; Inf, Inf, 6077000];
loglog(X, containsvals);
legend({"LinkedList", "ArrayList", "HashSet"});
title("Zeit, um auf das Vorhandensein von n Elementen (bei 100fach größerer Collection) zu testen");
xlabel("n");
ylabel("Zeit in Nanosekunden");
grid on;

print -djpg ContainsDiagram.jpg
