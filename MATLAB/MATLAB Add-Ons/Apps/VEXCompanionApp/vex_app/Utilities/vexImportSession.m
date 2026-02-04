function mat_name=vexImportSession
%% Callback for the VEX SD Replay Simulink block

% Copyright 2019 The MathWorks Inc.

% This function provides user interfaces to identify and select Robot Sessions
% recorded using the VEX SD Record block. It also stitches together
% multiple mat files corresponding to the same session and saves them in
% your current directory.

mat_name=[];

% Get SD card path
sdpath=uigetdir([],'Select SD Card');
if ~sdpath
    msgbox('No folder selected. Data was not imported','Error no folder','error');
else
    % Identify mat files
    dir_prefix = [sdpath filesep];
    mat_list=dir([dir_prefix '*.mat']);
    % Run mat file stitcher on files with multiple recordings
    VEXV5_MAT_stitcher(sdpath);
    
    if(~isempty(mat_list))
        % Identify the number of sessions available in the directory
        mat_list_length = length(mat_list);
        for j = mat_list_length:-1:1
            if  (isequal(mat_list(j).name(end-3:end), '.mat'))
                %Ignore the files if it is not a MAT file
                mat_temp_list(j).('name') = mat_list(j).name;
                [mat_temp_list(j).('core_name'), mat_temp_list(j).('idx')] = ...
                    extract_core_name(mat_list(j).name);
            end
        end
        mat_temp_list(isnan([mat_temp_list.idx])) = [];
        numSessions=0;
        sessionNames=[];
        % Create session names for all unique robot sessions and save
        % single files that correspond to a one complete recording
        for i=1:length(mat_temp_list)
            if isequal(mat_temp_list(i).idx,1)
                if i < length(mat_temp_list)
                    if isequal(mat_temp_list(i).core_name,mat_temp_list(i+1).core_name)
                        % Session with multiple files
                        numSessions=numSessions + 1;
                        sessionNames{numSessions}=[mat_temp_list(i).name(1:end-5) '_stitched'];
                    else
                        % Single unique file
                        current_load = load([dir_prefix mat_temp_list(i).name]);
                        save(mat_temp_list(i).name(1:end-6), '-struct', 'current_load');
                        numSessions=numSessions + 1;
                        sessionNames{numSessions}=[mat_temp_list(i).name(1:end-6)];
                    end
                else
                    % Last unique session in the directory
                    current_load = load([dir_prefix mat_temp_list(i).name]);
                    save(mat_temp_list(i).name(1:end-6), '-struct', 'current_load');
                    numSessions=numSessions + 1;
                    sessionNames{numSessions}=[mat_temp_list(i).name(1:end-6)];
                end
            end
        end
        if isempty(sessionNames)
            msgbox('No valid recording sessions found','Error no valid sessions','warn');
        end
    else
        msgbox('No recording sessions found','Error empty directory','warn');
    end
    
    % Provide a user interface for selecting from the available imported
    % robot sessions
    [idx,tf] = listdlg('ListString',sessionNames,'SelectionMode','single',...
        'ListSize',[250,150],'PromptString','Select the robot session:','Name','Select Robot Session');
    if tf
        mat_name=sessionNames{idx};
    else
        msgbox('No session selected. Data was not imported.','Error no session','error')
    end
end
end


function [core_name, idx] = extract_core_name(mat_name)
inc = 4;
idx_str = [];
while ~isnan(str2double(mat_name(end-inc)))
    idx_str = [mat_name(end-inc) idx_str]; %#ok<AGROW>
    inc  = inc + 1;
end
core_name = mat_name(1:end-inc);
idx = str2double(idx_str);
end