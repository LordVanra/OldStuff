% Monte Carlo Simulation of Penny Distribution
% Based on research-backed parameters for different storage locations
% Number of simulations: 1,000,000 per category

clear; clc;

% Set random seed for reproducibility
rng(42);

% Number of simulations
n = 1000000;

%% 1. WALLET - Poisson(λ = 6.92)
% Source: Pudwell & Rowland "What's in Your Wallet?"
lambda_wallet = 6.92;
wallet_pennies = poissrnd(lambda_wallet, n, 1);

%% 2. COIN JAR - Lognormal(μ = 4.3, σ = 1.2)
% Source: U.S. household coin jar data (median ~$60-$90)
% Convert dollars to pennies: median $75 = 7500 pennies
mu_jar = 5.717;  % ln(median in pennies)
sigma_jar = 1.2;
coinjar_pennies = lognrnd(mu_jar, sigma_jar, n, 1);

%% 3. CAR - Bernoulli + Negative Binomial
% Source: Car spare change survey (88% had coins)
% Step 1: Bernoulli for presence of any cash
p_car_presence = 0.88;
car_has_cash = rand(n, 1) < p_car_presence;

% Step 2: If cash present, negative binomial for number of pennies
% Target: mean ≈ 2, variance ≈ 3-4
mean_car_pennies = 2;
var_car_pennies = 3.5;

% Convert to negative binomial parameters
% mean = r(1-p)/p, variance = r(1-p)/p^2
% Solving: r = mean^2 / (var - mean)
r_car = mean_car_pennies^2 / (var_car_pennies - mean_car_pennies)
p_car_nb = mean_car_pennies / var_car_pennies


car_pennies = zeros(n, 1);
car_pennies(car_has_cash) = nbinrnd(r_car, p_car_nb, sum(car_has_cash), 1);

%% 4. DRAWER - Bernoulli + Poisson
% Source: U.S. household coin accumulation patterns
% Step 1: Bernoulli for whether drawer contains any cash
p_drawer_presence = 0.95;
drawer_has_cash = rand(n, 1) < p_drawer_presence;

% Step 2: If cash present, Poisson for number of pennies
lambda_drawer = 53;

drawer_pennies = zeros(n, 1);
drawer_pennies(drawer_has_cash) = poissrnd(lambda_drawer, sum(drawer_has_cash), 1);

%% 5. OTHER - Bernoulli(p=0.75) + Poisson(λ=15)
% Source: Surveys on spare change presence
p_presence = 0.75;
lambda_other = 15;

% Bernoulli trial for presence
presence = rand(n, 1) < p_presence;

% Poisson count given presence
other_pennies = zeros(n, 1);
other_pennies(presence) = poissrnd(lambda_other, sum(presence), 1);

%% SUMMARY STATISTICS
fprintf('=== PENNY DISTRIBUTION SIMULATION RESULTS ===\n\n');

fprintf('WALLET (Poisson λ=6.92):\n');
fprintf('  Mean: %.2f pennies\n', mean(wallet_pennies));
fprintf('  Std Dev: %.2f\n', std(wallet_pennies));
fprintf('  Median: %.2f\n', median(wallet_pennies));
fprintf('  Range: [%d, %d]\n\n', min(wallet_pennies), max(wallet_pennies));

fprintf('COIN JAR (Lognormal μ=%.2f, σ=%.2f):\n', mu_jar, sigma_jar);
fprintf('  Mean: %.2f pennies ($%.2f)\n', mean(coinjar_pennies), mean(coinjar_pennies)/100);
fprintf('  Std Dev: %.2f\n', std(coinjar_pennies));
fprintf('  Median: %.2f pennies ($%.2f)\n', median(coinjar_pennies), median(coinjar_pennies)/100);
fprintf('  Range: [%.0f, %.0f]\n\n', min(coinjar_pennies), max(coinjar_pennies));

fprintf('CAR (Bernoulli p=0.88 + Negative Binomial):\n');
fprintf('  Mean: %.2f pennies\n', mean(car_pennies));
fprintf('  Std Dev: %.2f\n', std(car_pennies));
fprintf('  Median: %.2f\n', median(car_pennies));
fprintf('  %% with zero: %.1f%%\n', 100*sum(car_pennies==0)/n);
fprintf('  Range: [%d, %d]\n\n', min(car_pennies), max(car_pennies));

fprintf('DRAWER (Bernoulli p=0.95 + Poisson λ=53):\n');
fprintf('  Mean: %.2f pennies\n', mean(drawer_pennies));
fprintf('  Std Dev: %.2f\n', std(drawer_pennies));
fprintf('  Median: %.2f\n', median(drawer_pennies));
fprintf('  %% with zero: %.1f%%\n', 100*sum(drawer_pennies==0)/n);
fprintf('  Range: [%d, %d]\n\n', min(drawer_pennies), max(drawer_pennies));

fprintf('OTHER (Bernoulli p=0.75 + Poisson λ=15):\n');
fprintf('  Mean: %.2f pennies\n', mean(other_pennies));
fprintf('  Std Dev: %.2f\n', std(other_pennies));
fprintf('  Median: %.2f\n', median(other_pennies));
fprintf('  %% with zero: %.1f%%\n\n', 100*sum(other_pennies==0)/n);

% Total pennies per simulation
total_pennies = wallet_pennies + coinjar_pennies + car_pennies + ...
                drawer_pennies + other_pennies;

fprintf('TOTAL PENNIES PER PERSON:\n');
fprintf('  Mean: %.2f pennies ($%.2f)\n', mean(total_pennies), mean(total_pennies)/100);
fprintf('  Std Dev: %.2f\n', std(total_pennies));
fprintf('  Median: %.2f pennies ($%.2f)\n', median(total_pennies), median(total_pennies)/100);
fprintf('  IQR: [%.0f, %.0f]\n\n', prctile(total_pennies, 25), prctile(total_pennies, 75));
fprintf('  90%% CI: [%.0f, %.0f]\n\n', prctile(total_pennies, 5), prctile(total_pennies, 95));
fprintf('  95%% CI: [%.0f, %.0f]\n\n', prctile(total_pennies, 2.5), prctile(total_pennies, 97.5));
fprintf('  99%% CI: [%.0f, %.0f]\n\n', prctile(total_pennies, 0.5), prctile(total_pennies, 99.50));
fprintf('  99.9%% CI: [%.0f, %.0f]\n\n', prctile(total_pennies, 0.05), prctile(total_pennies, 99.95));

%% VISUALIZATIONS
figure('Position', [100, 100, 1400, 900]);

% Wallet
subplot(3, 3, 1);
histogram(wallet_pennies,  'BinMethod', 'integers', 'FaceColor', [0.2 0.4 0.8]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', []);
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Coin Jar
subplot(3, 3, 2);
histogram(coinjar_pennies, 100, 'FaceColor', [0.8 0.4 0.2]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', []);
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Car
subplot(3, 3, 3);
histogram(min(car_pennies, 50), 'BinMethod', 'integers', 'FaceColor', [0.2 0.8 0.4]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', [])
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Drawer
subplot(3, 3, 4);
histogram(drawer_pennies, 'BinMethod', 'integers', 'FaceColor', [0.8 0.2 0.8]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', []);
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Other
subplot(3, 3, 5);
histogram(other_pennies, 'BinMethod', 'integers', 'FaceColor', [0.6 0.6 0.2]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', []);
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Total Distribution
subplot(3, 3, 6);
histogram(total_pennies, 100, 'BinWidth', 20, 'FaceColor', [0.4 0.4 0.4]);
grid off;
set(gca, 'XTickLabel', [], 'YTickLabel', []);
box on;
ax = gca;
ax.TickDir = 'out';
ax.XAxis.TickLength = [0.015 0];
ax.YAxis.TickLength = [0.015 0];

% Box plot comparison
subplot(3, 3, [7 8 9]);
data_matrix = [min(coinjar_pennies,1000)];
boxplot(data_matrix, 'Labels', {'Jar (capped)'});
set(gca, 'XTickLabel', [], 'YTickLabel', []);
grid off;

sgtitle('Monte Carlo Simulation: 100,000 Samples per Distribution', 'FontSize', 14, 'FontWeight', 'bold');

%% EXPORT DATA (optional)
% Uncomment to save results to CSV
% results_table = table(wallet_pennies, coinjar_pennies, car_pennies, ...
%                       drawer_pennies, other_pennies, total_pennies, ...
%                       'VariableNames', {'Wallet', 'CoinJar', 'Car', ...
%                       'Drawer', 'Other', 'Total'});
% writetable(results_table, 'penny_simulation_results.csv');