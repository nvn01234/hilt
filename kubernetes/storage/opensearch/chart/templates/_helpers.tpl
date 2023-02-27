{{/*
Common labels
*/}}
{{- define "common.labels" -}}
helm.sh/chart: {{ include "opensearch.chart" . }}
{{ include "common.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- with .Values.labels }}
{{ toYaml . }}
{{- end }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "common.selectorLabels" -}}
app.kubernetes.io/name: {{ include "opensearch.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "opensearch.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "opensearch.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "opensearch.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "opensearch.uname" -}}
{{- if empty .Values.fullnameOverride -}}
{{- if empty .Values.nameOverride -}}
{{ .Values.clusterName }}-{{ .Values.nodeGroup }}
{{- else -}}
{{ .Values.nameOverride }}-{{ .Values.nodeGroup }}
{{- end -}}
{{- else -}}
{{ .Values.fullnameOverride }}
{{- end -}}
{{- end -}}

{{- define "opensearch.masterService" -}}
{{- if empty .Values.masterService -}}
{{- if empty .Values.fullnameOverride -}}
{{- if empty .Values.nameOverride -}}
{{ .Values.clusterName }}-master
{{- else -}}
{{ .Values.nameOverride }}-master
{{- end -}}
{{- else -}}
{{ .Values.fullnameOverride }}
{{- end -}}
{{- else -}}
{{ .Values.masterService }}
{{- end -}}
{{- end -}}

{{- define "common.serviceName" -}}
{{- if eq .Values.nodeGroup "master" }}
{{- include "opensearch.masterService" . }}
{{- else }}
{{- include "opensearch.uname" . }}
{{- end }}
{{- end -}}

// {{- define "opensearch.endpoints" -}}
// {{- $replicas := int (toString (.Values.replicas)) }}
// {{- $uname := (include "opensearch.uname" .) }}
//   {{- range $i, $e := untilStep 0 $replicas 1 -}}
// {{ $uname }}-{{ $i }},
//   {{- end -}}
// {{- end -}}

{{/*
Create the name of the service account to use
*/}}
{{- define "opensearch.serviceAccountName" -}}
{{- if .Values.rbac.create -}}
{{ default (include "opensearch.fullname" .) .Values.rbac.serviceAccountName }}
{{- else -}}
"default"
{{- end -}}
{{- end -}}

{{- define "opensearch.roles" -}}
{{- range $.Values.roles -}}
{{ . }},
{{- end -}}
{{- end -}}

{{/*
Checksum pod annotations
*/}}
{{- define "opensearch.checksum" -}}
checksum/opensearch-properties: {{ include (print .Template.BasePath "/commons/opensearch-config.yaml") . | sha256sum | trunc 63 }}
{{- end -}}
